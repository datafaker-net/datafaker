package net.datafaker.providers.movie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChuckNorrisTest extends MovieFakerTest {

    @Test
    void fact() {
        assertThat(faker.chuckNorris().fact()).isNotEmpty();
    }
}
