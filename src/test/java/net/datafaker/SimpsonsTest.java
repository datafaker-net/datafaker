package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpsonsTest extends AbstractFakerTest {

    @Test
    void character() {
        assertThat(faker.simpsons().character()).isNotEmpty();
    }

    @Test
    void location() {
        assertThat(faker.simpsons().location()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.simpsons().quote()).isNotEmpty();
    }
}
