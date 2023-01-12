package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class LebowskiTest extends ShowFakerTest {
    @Test
    void actor() {
        assertThat(faker.lebowski().actor()).matches("^([\\w]+ ?){1,3}$");
    }

    @Test
    void character() {
        assertThat(faker.lebowski().character()).matches("^([\\w]+ ?){1,3}$");
    }

    @Test
    void quote() {
        assertThat(faker.lebowski().quote()).matches("^([\\w.,!?'-]+ ?)+$");
    }
}
