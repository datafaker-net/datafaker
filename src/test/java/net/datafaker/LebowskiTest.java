package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LebowskiTest extends AbstractFakerTest {
    @Test
    public void actor() {
        assertThat(faker.lebowski().actor()).matches("^([\\w]+ ?){1,3}$");
    }

    @Test
    public void character() {
        assertThat(faker.lebowski().character()).matches("^([\\w]+ ?){1,3}$");
    }

    @Test
    public void quote() {
        assertThat(faker.lebowski().quote()).matches("^([\\w.,!?'-]+ ?)+$");
    }
}
