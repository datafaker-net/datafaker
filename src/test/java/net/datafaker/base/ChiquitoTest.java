package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ChiquitoTest extends BaseFakerTest<BaseFaker> {

    @Test
    public void expressions() {
        assertThat(faker.chiquito().expressions()).isNotEmpty();
    }

    @Test
    public void terms() {
        assertThat(faker.chiquito().terms()).isNotEmpty();
    }

    @Test
    public void sentences() {
        assertThat(faker.chiquito().sentences()).isNotEmpty();
    }

    @Test
    public void jokes() {
        assertThat(faker.chiquito().jokes()).isNotEmpty();
    }

}
