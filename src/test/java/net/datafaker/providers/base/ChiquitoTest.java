package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChiquitoTest extends BaseFakerTest<BaseFaker> {

    @Test
    void expressions() {
        assertThat(faker.chiquito().expressions()).isNotEmpty();
    }

    @Test
    void terms() {
        assertThat(faker.chiquito().terms()).isNotEmpty();
    }

    @Test
    void sentences() {
        assertThat(faker.chiquito().sentences()).isNotEmpty();
    }

    @Test
    void jokes() {
        assertThat(faker.chiquito().jokes()).isNotEmpty();
    }

}
