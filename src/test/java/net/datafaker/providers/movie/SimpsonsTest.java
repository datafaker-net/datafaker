package net.datafaker.providers.movie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class SimpsonsTest extends MovieFakerTest {

    @Test
    void character() {
        Assertions.assertThat(faker.simpsons().character()).isNotEmpty();
    }

    @Test
    void location() {
        Assertions.assertThat(faker.simpsons().location()).isNotEmpty();
    }

    @Test
    void quote() {
        Assertions.assertThat(faker.simpsons().quote()).isNotEmpty();
    }
}
