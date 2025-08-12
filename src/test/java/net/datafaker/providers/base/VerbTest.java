package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VerbTest {

    public static final String WORDS = "[\\w-]+";
    private final Faker faker = new Faker();

    @RepeatedTest(10)
    void testBase() {
        assertThat(faker.verb().base()).matches(WORDS);
    }

    @RepeatedTest(10)
    void testPast() {
        assertThat(faker.verb().past()).matches(WORDS);
    }

    @RepeatedTest(10)
    void testPastParticiple() {
        assertThat(faker.verb().pastParticiple()).matches(WORDS);
    }

    @RepeatedTest(10)
    void testSimplePresent() {
        assertThat(faker.verb().simplePresent()).matches(WORDS);
    }

    @RepeatedTest(10)
    void testIngForm() {
        assertThat(faker.verb().ingForm()).matches(WORDS);
    }
}
