package net.datafaker;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.nio.file.Files.newInputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class NoDuplicatesInYmlTest {

    @ParameterizedTest
    @MethodSource("ymlFiles")
    void noDuplicatesInArrays(Path file) {
        try (InputStream in = newInputStream(file)) {
            Object ymlContent = new Yaml().load(in);
            var errors = findDuplicates(ymlContent);
            assertThat(errors)
                .overridingErrorMessage(() -> "Duplicates in file %s: %s".formatted(file, errors))
                .isEmpty();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse %s: %s".formatted(file, e.getMessage()), e);
        }
    }

    @SuppressWarnings("unused")
    private static Collection<Path> ymlFiles() throws IOException {
        List<Path> ymlFiles = ymlFiles(Paths.get("src"));
        assertThat(ymlFiles).as("No *.yml files found").isNotEmpty();
        return ymlFiles;
    }

    private static List<Path> ymlFiles(Path root) throws IOException {
        try (var stream = Files.walk(root)) {
            return stream
                .filter(p -> p.toString().endsWith(".yml"))
                .toList();
        }
    }

    private Map<String, Set<Object>> findDuplicates(Object node) {
        Map<String, Set<Object>> errors = new HashMap<>();
        findDuplicatesRecursively(node, "", errors);
        return errors;
    }

    private void findDuplicatesRecursively(Object node, String path, Map<String, Set<Object>> errors) {
        if (node instanceof Map<?, ?> map) {
            for (var entry : map.entrySet()) {
                String key = (String) entry.getKey();
                findDuplicatesRecursively(entry.getValue(), path + "/" + key, errors);
            }
        } else if (node instanceof List<?> list) {
            Set<Object> seen = new HashSet<>();
            for (Object value : list) {
                if (!seen.add(value) && !areDuplicatesAllowed(path)) {
                    errors.computeIfAbsent(path, k -> new HashSet<>()).add(value);
                }
                findDuplicatesRecursively(value, path + "[]", errors);
            }
        }
    }

    /**
     * For some specific data, we use duplicates to generate realistic data.
     * We want to generate common names (e.g. "Karel Spiel") more often than rare names (e.g. "Karel van der Spiel").
     */
    private boolean areDuplicatesAllowed(String path) {
        return path.endsWith("/faker/name/name") ||
            path.endsWith("/faker/name/name_with_middle") ||
            path.endsWith("/faker/job/title") ||
            path.endsWith("/faker/company/name");
    }
}
