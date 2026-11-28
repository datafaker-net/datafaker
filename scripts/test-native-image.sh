#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")/.."

if [[ -z "${JAVA_HOME:-}" || ! -x "$JAVA_HOME/bin/native-image" ]]; then
    echo "JAVA_HOME must point to a GraalVM installation containing native-image." >&2
    exit 1
fi

./mvnw --batch-mode -PnoGpg -Dmaven.test.skip=true -Dmaven.javadoc.skip=true clean package \
    org.apache.maven.plugins:maven-dependency-plugin:3.7.0:build-classpath \
    -DincludeScope=runtime -Dmdep.outputFile=target/native-image-smoke-classpath.txt

jar=$(find target -maxdepth 1 -name 'datafaker-*.jar' ! -name '*-sources.jar' ! -name '*-javadoc.jar')
classpath="$jar:$(cat target/native-image-smoke-classpath.txt)"
smoke_dir=target/native-image-smoke
mkdir -p "$smoke_dir"

"$JAVA_HOME/bin/javac" --release 17 -cp "$classpath" -d "$smoke_dir" scripts/NativeImageSmokeTest.java

build_flags=()
run_flags=()
help=$("$JAVA_HOME/bin/native-image" --help)
if [[ "$help" == *--no-fallback* ]]; then
    build_flags+=(--no-fallback)
fi
if [[ "$help" == *--exact-reachability-metadata* ]]; then
    build_flags+=(--exact-reachability-metadata=net.datafaker)
    run_flags+=(-XX:MissingRegistrationReportingMode=Exit)
fi

"$JAVA_HOME/bin/native-image" "${build_flags[@]}" "$@" \
    -cp "$smoke_dir:$classpath" -o "$smoke_dir/datafaker-smoke" NativeImageSmokeTest
"$smoke_dir/datafaker-smoke" "${run_flags[@]}"
