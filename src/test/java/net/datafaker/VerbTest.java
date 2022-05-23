package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VerbTest extends AbstractFakerTest {

    @Test
    void testBase() {
        assertThat(faker.verb().base()).matches("\\w+");
    }

    @Test
    void testPast() {
        assertThat(faker.verb().past()).matches("\\w+");
    }

    @Test
    void testPastParticiple() {
        assertThat(faker.verb().pastParticiple()).matches("\\w+");
    }

    @Test
    void testSimplePresent() {
        assertThat(faker.verb().simplePresent()).matches("\\w+");
    }

    @Test
    void testIngForm() {
        assertThat(faker.verb().ingForm()).matches("\\w+");
    }
}
