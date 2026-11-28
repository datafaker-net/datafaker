package net.datafaker.providers.base;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AdjectiveTest extends BaseFakerTest {

    private final Adjective adjective = faker.adjective();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(adjective::positive, "adjective.positive"),
            TestSpec.of(adjective::negative, "adjective.negative")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"en", "tr"})
    void anySamplesBothSentimentLists(String languageTag) {
        BaseFaker localizedFaker = new BaseFaker(Locale.forLanguageTag(languageTag), new Random(42));
        List<String> positive = localizedFaker.fakeValuesService()
            .fetchObject("adjective.positive", localizedFaker.getContext());
        List<String> negative = localizedFaker.fakeValuesService()
            .fetchObject("adjective.negative", localizedFaker.getContext());

        Adjective localizedAdjective = localizedFaker.adjective();
        List<String> generated = Stream.generate(localizedAdjective::any).limit(20).toList();

        assertThat(generated)
            .allMatch(value -> positive.contains(value) || negative.contains(value))
            .anyMatch(value -> positive.contains(value) && !negative.contains(value))
            .anyMatch(value -> negative.contains(value) && !positive.contains(value));
    }
}
