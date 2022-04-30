package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HackerTest extends AbstractFakerTest {

    @Test
    void testAbbreviation() {
        assertThat(faker.hacker().abbreviation()).matches("[A-Z]{2,4}");
    }

    @Test
    void testAdjective() {
        assertThat(faker.hacker().adjective()).matches("(\\w+[- ]?){1,2}");
    }

    @Test
    void testNoun() {
        assertThat(faker.hacker().noun()).matches("\\w+( \\w+)?");
    }

    @Test
    void testVerb() {
        assertThat(faker.hacker().verb()).matches("\\w+( \\w+)?");
    }

    @Test
    void testIngverb() {
        assertThat(faker.hacker().ingverb()).matches("\\w+ing( \\w+)?");
    }
}
