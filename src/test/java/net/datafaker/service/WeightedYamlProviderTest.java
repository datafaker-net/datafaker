package net.datafaker.service;

import net.datafaker.providers.base.BaseFaker;
import net.datafaker.providers.base.Unique;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WeightedYamlProviderTest {
    @TempDir
    Path directory;

    @Test
    void resolvesCustomWeightedYamlAndPreservesOrdinaryValues() {
        BaseFaker faker = custom(new Random(1));
        faker.addUrl(Locale.ENGLISH, getClass().getResource("/weighted-custom.yml"));
        for (int i = 0; i < 20; i++) {
            assertThat(faker.resolve("weighted_custom.strings")).isIn("first", "second");
            assertThat(faker.resolve("weighted_custom.domains")).isIn(".it", ".ee", ".lv", ".lt");
            assertThat(faker.resolve("weighted_custom.local_template")).isEqualTo("chosen!");
            assertThat(faker.resolve("weighted_custom.reference")).isNotEmpty();
            assertThat(faker.resolve("weighted_custom.ordinary")).isIn("a", "b");
            assertThat(faker.resolve("weighted_custom.scalar")).isEqualTo("plain");
            assertThat(faker.resolve("weighted_custom.nested.value")).isEqualTo("nested");
        }
        assertThat(faker.fakeValuesService().fetchString("weighted_custom.strings", faker.getContext())).isIn("first", "second");
        Object ordinary = faker.fakeValuesService().fetchObject("weighted_custom.ordinary", faker.getContext());
        assertThat(ordinary).isEqualTo(List.of("a", "b"));
        assertThat(faker.fakeValuesService().safeFetch("weighted_custom.empty", faker.getContext(), "fallback")).isEqualTo("fallback");
        // Custom data is merged with the ordinary locale fallback, not substituted for it.
        assertThat(faker.resolve("name.first_name")).isNotEmpty();
    }

    @Test
    void sameSeedProducesSameSequence() {
        BaseFaker first = custom(new Random(123));
        BaseFaker second = custom(new Random(123));
        for (BaseFaker faker : List.of(first, second)) {
            faker.addUrl(Locale.ENGLISH, getClass().getResource("/weighted-custom.yml"));
        }
        for (int i = 0; i < 100; i++) {
            assertThat(first.resolve("weighted_custom.domains")).isEqualTo(second.resolve("weighted_custom.domains"));
        }
    }

    @Test
    void customSourcesAndRandomServicesAreIsolated() throws IOException {
        Path a = directory.resolve("a.yml");
        Path b = directory.resolve("b.yml");
        Files.writeString(a, "en:\n  faker:\n    weighted_custom:\n      value:\n        - '100%': 'source-a'\n");
        Files.writeString(b, "en:\n  faker:\n    weighted_custom:\n      value:\n        - '100%': 'source-b'\n");
        BaseFaker first = custom(new Random(1));
        BaseFaker second = custom(new Random(1));
        first.addPath(Locale.ENGLISH, a);
        second.addPath(Locale.ENGLISH, b);
        assertThat(first.resolve("weighted_custom.value")).isEqualTo("source-a");
        assertThat(second.resolve("weighted_custom.value")).isEqualTo("source-b");
        first.getContext().setRandomService(new RandomService(new Random(2)));
        assertThat(first.resolve("weighted_custom.value")).isEqualTo("source-a");
    }

    @Test
    void preservesIntegerKeysInOrdinaryNestedMaps() throws IOException {
        Path file = directory.resolve("integer-keys.yml");
        Files.writeString(file, """
            en:
              faker:
                weighted_custom:
                  sibling: 'ordinary sibling'
                  original:
                    1: 'one'
                    2: ['two', 'deux']
            """);
        BaseFaker faker = custom(new Random(1));
        faker.addPath(Locale.ENGLISH, file);
        assertThat(faker.resolve("weighted_custom.sibling")).isEqualTo("ordinary sibling");
        Object original = faker.fakeValuesService().fetchObject("weighted_custom.original", faker.getContext());
        assertThat(original).isEqualTo(Map.of(1, "one", 2, List.of("two", "deux")));
    }

    @Test
    void malformedCustomYamlFailsClearly() throws IOException {
        Path bad = directory.resolve("bad.yml");
        Files.writeString(bad, "en:\n  faker:\n    weighted_custom:\n      value:\n        - '60%': 'a'\n        - '30%': 'b'\n");
        BaseFaker faker = custom(new Random(1));
        assertThatThrownBy(() -> {
            faker.addPath(Locale.ENGLISH, bad);
            faker.resolve("weighted_custom.value");
        })
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("percentages must total 100")
            .hasMessageContaining("weighted_custom.value")
            .hasMessageContaining(bad.toUri().toURL().toString());
    }

    @Test
    void loadingReportsBrokenNestedKeyAlongsideValidSibling() throws IOException {
        Path bad = directory.resolve("nested-bad.yml");
        Files.writeString(bad, """
            en:
              faker:
                weighted_custom:
                  sibling: 'valid'
                  nested:
                    value:
                    - 60%: 'a'
                    - 30%: 'b'
            """);
        BaseFaker faker = custom(new Random(1));
        assertThatThrownBy(() -> {
            faker.addPath(Locale.ENGLISH, bad);
            faker.resolve("weighted_custom.sibling");
        })
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("weighted_custom.nested.value")
            .hasMessageContaining(bad.toUri().toURL().toString())
            .hasMessageContaining("percentages must total 100");
    }

    @Test
    void weightedRegexTemplatesResolveLikeScalarRegexValues() {
        BaseFaker faker = custom(new Random(1));
        faker.addUrl(Locale.ENGLISH, getClass().getResource("/weighted-custom.yml"));
        for (String key : List.of("regex_string", "regex_list")) {
            assertThat(faker.fakeValuesService().safeFetch("weighted_custom." + key, faker.getContext(), null))
                .isEqualTo("#{regexify '[A-Z]{3}'}");
            assertThat(faker.expression("#{weighted_custom." + key + "}")).matches("[A-Z]{3}");
        }
    }

    @Test
    void weightedValuesAreNotEnumerableByUnique() {
        BaseFaker faker = custom(new Random(1));
        faker.addUrl(Locale.ENGLISH, getClass().getResource("/weighted-custom.yml"));
        Object raw = faker.fakeValuesService().fetchObject("weighted_custom.strings", faker.getContext());
        assertThat(raw).isInstanceOf(WeightedYamlValues.class);
        assertThatThrownBy(() -> new Unique(faker).fetchFromYaml("weighted_custom.strings"))
            .isInstanceOf(NoSuchElementException.class).hasMessageContaining("No values found");
    }

    private static BaseFaker custom(Random random) {
        return new BaseFaker(Locale.ENGLISH, random);
    }
}
