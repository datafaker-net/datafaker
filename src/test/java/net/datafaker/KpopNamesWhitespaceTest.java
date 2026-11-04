package net.datafaker;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.newInputStream;
import static org.assertj.core.api.Assertions.assertThat;

class KpopNamesWhitespaceTest {

    @Test
    @SuppressWarnings("unchecked")
    void kpopNamesHaveNoSurroundingWhitespace() throws IOException {
        Map<String, Object> yml;
        try (var in = newInputStream(Path.of("src/main/resources/en/kpop.yml"))) {
            yml = new Yaml().load(in);
        }
        var faker = (Map<String, Object>) ((Map<String, Object>) yml.get("en")).get("faker");
        var kpop = (Map<String, Object>) faker.get("kpop");

        assertThat(kpop).isNotEmpty();
        kpop.forEach((key, names) ->
            assertThat((List<?>) names)
                .as("kpop.%s", key)
                .allMatch(name -> name.toString().equals(name.toString().strip())));
    }
}
