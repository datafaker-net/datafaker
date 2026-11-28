package net.datafaker;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.nio.file.Files.newInputStream;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Every string value in every {@code src/main/resources} YAML file must be free of leading and trailing whitespace
 * ({@code value.equals(value.strip())}), whether or not a provider test references the key.
 * <p>
 * Where whitespace is intentional, add an entry to {@link #ALLOWED}. An entry is scoped to one key path and only
 * the values matching its pattern are exempt, so other values under that key (e.g. {@code "Rijn "}) still fail.
 */
class YmlOuterWhitespaceTest {

    /**
     * @param keyPath    dot separated key path below {@code <locale>.faker.} the exemption applies to
     * @param valueRegex the values allowed to keep outer whitespace, matched against the whole value
     * @param reason     why the whitespace is intentional
     */
    private record Allowed(String keyPath, Pattern valueRegex, String reason) {
        Allowed(String keyPath, String valueRegex, String reason) {
            this(keyPath, Pattern.compile(valueRegex, Pattern.DOTALL), reason);
        }
    }

    private static final List<Allowed> ALLOWED = List.of(
        new Allowed("separator", " \\S+ ", "joins the last two items of a list, e.g. \"a & b\""),
        new Allowed("address.building_number", " \\S+", "appended to a street name, e.g. \"Carrer Major s/n.\""),
        new Allowed("address.city_suffix", " \\S.*\\S", "appended to a city name, e.g. \"Westervoort aan de Rijn\""),
        new Allowed("lorem.punctuation.space", "[ \u3000]", "the word separator itself"),
        new Allowed("http.response_body.markdown", "\\S.*\\S\n", "markdown documents end with a single newline")
    );

    @ParameterizedTest
    @MethodSource("ymlFiles")
    void noOuterWhitespaceInValues(Path file) throws IOException {
        Object content;
        try (var in = newInputStream(file)) {
            content = new Yaml().load(in);
        }
        List<String> violations = new ArrayList<>();
        collectViolations(content, "", violations);

        assertThat(violations)
            .as("Values with leading or trailing whitespace in %s (trim them or add an entry to ALLOWED)", file)
            .isEmpty();
    }

    private static void collectViolations(Object node, String path, List<String> violations) {
        if (node instanceof Map<?, ?> map) {
            map.forEach((key, value) -> collectViolations(value, path.isEmpty() ? String.valueOf(key) : path + "." + key, violations));
        } else if (node instanceof Iterable<?> items) {
            items.forEach(item -> collectViolations(item, path, violations));
        } else if (node instanceof String value && !value.equals(value.strip()) && !isAllowed(path, value)) {
            violations.add("%s: \"%s\"".formatted(path, value.replace("\n", "\\n")));
        }
    }

    private static boolean isAllowed(String path, String value) {
        // path looks like "nl.faker.address.city_suffix", drop the "<locale>.faker." prefix
        String keyPath = path.substring(path.indexOf(".faker.") + ".faker.".length());
        return ALLOWED.stream().anyMatch(a -> a.keyPath().equals(keyPath) && a.valueRegex().matcher(value).matches());
    }

    @SuppressWarnings("unused")
    private static List<Path> ymlFiles() throws IOException {
        try (var stream = Files.walk(Paths.get("src/main/resources"))) {
            List<Path> files = stream.filter(p -> p.toString().endsWith(".yml")).toList();
            assertThat(files).as("No *.yml files found").isNotEmpty();
            return files;
        }
    }
}
