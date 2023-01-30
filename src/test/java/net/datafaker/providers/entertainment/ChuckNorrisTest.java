package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChuckNorrisTest extends EntertainmentFakerTest {

    @Test
    void testFact() {
        assertThat(faker.chuckNorris().fact()).isNotEmpty();
    }
}
