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

    @Test
    void metadataContainsProductionEntriesOnly() throws IOException {
        Assumptions.assumeFalse(Boolean.getBoolean("native.image.metadata.generation"));

        Path metadataFile = Path.of(System.getProperty("native.image.metadata", DEFAULT_METADATA));
        Map<String, List<Map<String, Object>>> metadata;
        try (InputStream input = Files.newInputStream(metadataFile)) {
            metadata = new Yaml().load(input);
        }

        List<String> reflectionTypes = entries(metadata, "reflection").stream()
                .map(entry -> entry.get("type"))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();

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

    private static List<Map<String, Object>> entries(
            Map<String, List<Map<String, Object>>> metadata, String category) {
        return metadata.getOrDefault(category, List.of());
    }

    private static Path productionSourceFor(String type) {
        return Path.of("src/main/java", type.replace('.', '/') + ".java");
    }
}
