package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnimalTest extends AbstractFakerTest {

    @Test
    void name() {
        assertThat(faker.animal().name()).matches("[A-Za-z ]+");
    }

}
