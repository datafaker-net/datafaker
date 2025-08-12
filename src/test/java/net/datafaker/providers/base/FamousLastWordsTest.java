package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class FamousLastWordsTest {
    private final Faker faker = new Faker();

    @RepeatedTest(10)
    void testLastWords() {
        assertThat(faker.famousLastWords().lastWords()).matches("^[A-Za-z- .,'!?-…]+$");
    }
}
