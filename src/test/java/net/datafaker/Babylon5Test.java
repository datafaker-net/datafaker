package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Babylon5Test extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.babylon5().character()).isNotEmpty();
    }

    @Test
    public void quote() {
        assertThat(faker.babylon5().quote()).isNotEmpty();
    }
}
