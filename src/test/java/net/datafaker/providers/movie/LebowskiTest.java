package net.datafaker.providers.movie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class LebowskiTest extends MovieFakerTest {
    @Test
    void actor() {
        Assertions.assertThat(faker.lebowski().actor()).matches("^([\\w]+ ?){1,3}$");
    }

    @Test
    void character() {
        Assertions.assertThat(faker.lebowski().character()).matches("^([\\w]+ ?){1,3}$");
    }

    @Test
    void quote() {
        Assertions.assertThat(faker.lebowski().quote()).matches("^([\\w.,!?'-]+ ?)+$");
    }
}
