package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class VerbTest extends BaseFakerTest<BaseFaker> {

    public static final String WORDS = "[\\w-]+";

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
