#!/usr/bin/env bash

set -euo pipefail

project_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
generated_filter="$project_dir/target/native-image-test-classes-filter.json"
generated_metadata="$project_dir/target/native-image-agent/reachability-metadata.json"
runtime_classpath="$project_dir/target/native-image-runtime-classpath.txt"
runtime_resources="$project_dir/target/native-image-runtime-resources.json"
published_metadata="$project_dir/src/main/resources/META-INF/native-image/reachability-metadata.json"

if [[ -z "${JAVA_HOME:-}" || ! -x "$JAVA_HOME/bin/native-image" ]]; then
    echo "JAVA_HOME must point to a GraalVM installation containing native-image." >&2
    exit 1
fi
if ! command -v jq >/dev/null 2>&1; then
    echo "jq is required to clean the generated reachability metadata." >&2
    exit 1
fi

cd "$project_dir"

./mvnw clean test-compile
./mvnw org.apache.maven.plugins:maven-dependency-plugin:3.7.0:build-classpath \
    -DincludeScope=runtime \
    -Dmdep.outputFile="$runtime_classpath"

printf '{\n  "rules": [\n' > "$generated_filter"
first_rule=true
while IFS= read -r class_file; do
    class_name="${class_file#"$project_dir/target/test-classes/"}"
    class_name="${class_name%.class}"
    class_name="${class_name//\//.}"

    if [[ "$first_rule" == true ]]; then
        first_rule=false
    else
        printf ',\n' >> "$generated_filter"
    fi
    printf '    {"excludeClasses": "%s"}' "$class_name" >> "$generated_filter"
done < <(find "$project_dir/target/test-classes" -type f -name '*.class' | sort)
printf '\n  ]\n}\n' >> "$generated_filter"

additional_filters=",caller-filter-file=$generated_filter,access-filter-file=$generated_filter"
./mvnw -PnativeMetadata -Dnative.image.agent.additional.filter="$additional_filters" test

if [[ ! -f "$generated_metadata" ]]; then
    echo "The tracing agent did not create $generated_metadata." >&2
    exit 1
fi

{
    find "$project_dir/src/main/resources" -type f ! -path "$published_metadata" \
        | sed "s|$project_dir/src/main/resources/||"
    tr ':' '\n' < "$runtime_classpath" | while IFS= read -r dependency || [[ -n "$dependency" ]]; do
        "$JAVA_HOME/bin/jar" tf "$dependency" | sed \
            -e '/\/$/d' \
            -e '/\.class$/d' \
            -e '\|^META-INF/native-image/|d' \
            -e '\|^META-INF/MANIFEST.MF$|d' \
            -e '/\.DSA$/d' \
            -e '/\.RSA$/d' \
            -e '/\.SF$/d'
    done
} | sort -u | jq --raw-input . | jq --slurp . > "$runtime_resources"

jq --slurpfile runtimeResources "$runtime_resources" '
    .resources = (
        [(.resources // [])[] | select(has("module"))]
        + [$runtimeResources[0][] | {glob: .}]
        | unique_by([.module // "", .glob])
    )
    | with_entries(select(.value | type != "array" or length > 0))
' "$generated_metadata" > "$generated_metadata.tmp"
mv "$generated_metadata.tmp" "$generated_metadata"

./mvnw -PnoGpg -Djacoco.skip=true -Dtest=NativeImageMetadataTest \
    -Dnative.image.metadata="$generated_metadata" test

cp "$generated_metadata" "$published_metadata"

echo "Updated $published_metadata"
