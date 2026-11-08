package net.datafaker.service;

import net.datafaker.providers.base.BaseFaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
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
        assertThat(ordinary).isEqualTo(java.util.List.of("a", "b"));
        assertThat(faker.fakeValuesService().safeFetch("weighted_custom.empty", faker.getContext(), "fallback")).isEqualTo("fallback");
        // Custom data is merged with the ordinary locale fallback, not substituted for it.
        assertThat(faker.resolve("name.first_name")).isNotEmpty();
    }

    @Test
    void sameSeedAndSharedYamlDoNotShareSamples() {
        BaseFaker first = custom(new Random(123));
        BaseFaker second = custom(new Random(123));
        for (BaseFaker faker : java.util.List.of(first, second)) {
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
        assertThat(original).isEqualTo(java.util.Map.of(1, "one", 2, java.util.List.of("two", "deux")));
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
            .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("percentages must total 100");
    }

    private static BaseFaker custom(Random random) {
        return new BaseFaker(Locale.ENGLISH, random);
    }
}
