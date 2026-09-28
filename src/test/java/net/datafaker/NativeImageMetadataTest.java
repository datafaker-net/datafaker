package net.datafaker;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class NativeImageMetadataTest {
    private static final String DEFAULT_METADATA =
            "src/main/resources/META-INF/native-image/reachability-metadata.json";
    private static final List<String> TEST_DEPENDENCY_PREFIXES = List.of(
            "com.intellij.",
            "net.bytebuddy.",
            "org.assertj.",
            "org.apache.maven.",
            "org.junit.",
            "org.mockito.",
            "org.opentest4j.",
            "org.slf4j.simple.");
    /**
     * Inner types of {@link net.datafaker.service.FakeValuesService} used for expression resolution.
     * The tracing agent never records them because they are not accessed reflectively on a regular
     * JVM, but native images still need them registered (see issue #1737).
     */
    private static final List<String> EXPRESSION_RESOLUTION_TYPES = List.of(
            "net.datafaker.service.FakeValuesService$ConstantResolver",
            "net.datafaker.service.FakeValuesService$MethodAndCoercedArgs",
            "net.datafaker.service.FakeValuesService$MethodAndCoercedArgsResolver",
            "net.datafaker.service.FakeValuesService$MethodResolver",
            "net.datafaker.service.FakeValuesService$RegExpContext",
            "net.datafaker.service.FakeValuesService$SafeFetchResolver",
            "net.datafaker.service.FakeValuesService$ValueResolver");

    @Test
    void metadataContainsProductionEntriesOnly() throws IOException {
        Map<String, List<Map<String, Object>>> metadata = loadMetadata();

        Set<String> reflectionTypes = reflectionTypes(metadata);

        assertThat(reflectionTypes)
                .noneMatch(type -> TEST_DEPENDENCY_PREFIXES.stream().anyMatch(type::startsWith));
        reflectionTypes.stream()
                .filter(type -> type.startsWith("net.datafaker."))
                .map(type -> type.split("\\$", 2)[0])
                .distinct()
                .forEach(type -> assertThat(productionSourceFor(type))
                        .as("production source for native-image reflection type %s", type)
                        .exists());

        assertThat(entries(metadata, "reflection").toString())
                .doesNotContain("net.bytebuddy.");
        assertThat(entries(metadata, "resources").toString())
                .doesNotContain("org.assertj", "org.junit", "org.mockito", "mockito-extensions", "test.yml", "test/test",
                        "junit-platform.properties", "logging.properties", "org/slf4j/impl/StaticLoggerBinder.class");
        assertThat(entries(metadata, "jni").toString())
                .doesNotContain("com.intellij", "net.bytebuddy", "org.assertj", "org.junit", "org.mockito");
        assertThat(entries(metadata, "serialization").toString())
                .doesNotContain("com.intellij", "net.bytebuddy", "org.assertj", "org.junit", "org.mockito");
    }

    @Test
    void metadataRegistersExpressionResolutionTypes() throws IOException {
        Map<String, List<Map<String, Object>>> metadata = loadMetadata();

        assertThat(reflectionTypes(metadata))
                .containsAll(EXPRESSION_RESOLUTION_TYPES);

        assertThat(entries(metadata, "reflection"))
                .filteredOn(entry -> EXPRESSION_RESOLUTION_TYPES.contains(entry.get("type")))
                .allSatisfy(entry -> assertThat(entry)
                        .containsEntry("allDeclaredConstructors", true)
                        .containsEntry("allDeclaredMethods", true)
                        .containsEntry("allDeclaredFields", true));
    }

    private static Map<String, List<Map<String, Object>>> loadMetadata() throws IOException {
        Assumptions.assumeFalse(Boolean.getBoolean("native.image.metadata.generation"));

        Path metadataFile = Path.of(System.getProperty("native.image.metadata", DEFAULT_METADATA));
        try (InputStream input = Files.newInputStream(metadataFile)) {
            return new Yaml().load(input);
        }
    }

    private static Set<String> reflectionTypes(Map<String, List<Map<String, Object>>> metadata) {
        return entries(metadata, "reflection").stream()
                .map(entry -> entry.get("type"))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .collect(Collectors.toSet());
    }

    private static List<Map<String, Object>> entries(
            Map<String, List<Map<String, Object>>> metadata, String category) {
        return metadata.getOrDefault(category, List.of());
    }

    private static Path productionSourceFor(String type) {
        return Path.of("src/main/java", type.replace('.', '/') + ".java");
    }
}
