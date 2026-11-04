package net.datafaker;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.newInputStream;
import static org.assertj.core.api.Assertions.assertThat;

class UsPostcodesByStateTest {

    @Test
    @SuppressWarnings("unchecked")
    void everyPostcodeByStateHasFiveDigits() throws IOException {
        Map<String, Object> yml;
        try (var in = newInputStream(Path.of("src/main/resources/_US.yml"))) {
            yml = new Yaml().load(in);
        }
        var faker = (Map<String, Object>) ((Map<String, Object>) yml.get("_US")).get("faker");
        var address = (Map<String, Object>) faker.get("address");
        var postcodesByState = (Map<String, List<?>>) address.get("postcode_by_state");

        assertThat(postcodesByState).isNotEmpty();
        postcodesByState.forEach((state, postcodes) ->
            assertThat(postcodes)
                .as("postcodes of %s", state)
                .allMatch(postcode -> String.valueOf(postcode).matches("[0-9]{5}")));
    }
}
