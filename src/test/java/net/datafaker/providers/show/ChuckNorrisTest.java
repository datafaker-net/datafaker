package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChuckNorrisTest extends ShowFakerTest {

    @Test
    void testFact() {
        assertThat(faker.chuckNorris().fact()).isNotEmpty();
    }
}
