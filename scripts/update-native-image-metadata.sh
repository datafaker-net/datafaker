#!/usr/bin/env bash

set -euo pipefail

project_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
generated_filter="$project_dir/target/native-image-test-classes-filter.json"
generated_metadata="$project_dir/target/native-image-agent/reachability-metadata.json"
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

# The Maven build supplies classpath resources. Preserve traced resources from JDK modules.
jq '
    .resources = [(.resources // [])[] | select(has("module"))]
    | with_entries(select(.value | type != "array" or length > 0))
' "$generated_metadata" > "$generated_metadata.tmp"
mv "$generated_metadata.tmp" "$generated_metadata"

./mvnw -PnoGpg -Djacoco.skip=true -Dtest=NativeImageMetadataTest \
    -Dnative.image.metadata="$generated_metadata" test

cp "$generated_metadata" "$published_metadata"

echo "Updated $published_metadata"
