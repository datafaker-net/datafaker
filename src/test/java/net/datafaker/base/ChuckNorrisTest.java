package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChuckNorrisTest extends AbstractBaseFakerTest {

    @Test
    void testFact() {
        assertThat(faker.chuckNorris().fact()).isNotEmpty();
    }
}
