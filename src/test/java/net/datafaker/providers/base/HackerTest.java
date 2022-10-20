package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HackerTest extends BaseFakerTest<BaseFaker> {

    @Test
    void abbreviation() {
        assertThat(faker.hacker().abbreviation()).matches("[A-Z]{2,4}");
    }

    @Test
    void adjective() {
        assertThat(faker.hacker().adjective()).matches("(\\w+[- ]?){1,2}");
    }

    @Test
    void noun() {
        assertThat(faker.hacker().noun()).matches("\\w+( \\w+)?");
    }

    @Test
    void verb() {
        assertThat(faker.hacker().verb()).matches("\\w+( \\w+)?");
    }

    @Test
    void ingverb() {
        assertThat(faker.hacker().ingverb()).matches("\\w+ing( \\w+)?");
    }
}
