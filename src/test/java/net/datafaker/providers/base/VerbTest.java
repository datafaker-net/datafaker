package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class VerbTest extends BaseFakerTest<BaseFaker> {

    public static final String WORDS = "[\\w-]+";

    @RepeatedTest(10)
    void base() {
        assertThat(faker.verb().base()).matches(WORDS);
    }

    @RepeatedTest(10)
    void past() {
        assertThat(faker.verb().past()).matches(WORDS);
    }

    @RepeatedTest(10)
    void pastParticiple() {
        assertThat(faker.verb().pastParticiple()).matches(WORDS);
    }

    @RepeatedTest(10)
    void simplePresent() {
        assertThat(faker.verb().simplePresent()).matches(WORDS);
    }

    @RepeatedTest(10)
    void ingForm() {
        assertThat(faker.verb().ingForm()).matches(WORDS);
    }
}
