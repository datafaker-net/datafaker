package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChuckNorrisTest extends AbstractFakerTest {

    @Test
    void testFact() {
        assertThat(faker.chuckNorris().fact()).isNotEmpty();
    }
}
