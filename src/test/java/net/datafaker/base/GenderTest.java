package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GenderTest extends BaseFakerTest<BaseFaker> {

    @Test
    void types() {
        assertThat(faker.gender().types()).matches("(\\w+ ?){1,2}");
    }

    @Test
    void binaryTypes() {
        assertThat(faker.gender().binaryTypes()).matches("[A-Za-z ]+");
    }

    @Test
    void shortBinaryTypes() {
        assertThat(faker.gender().shortBinaryTypes()).matches("[fm]");
    }

}
