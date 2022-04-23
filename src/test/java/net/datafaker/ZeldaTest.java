package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZeldaTest extends AbstractFakerTest {

    @Test
    public void game() {
        assertThat(faker.zelda().game()).matches("[A-Za-z'\\- :]+");
    }

    @Test
    public void character() {
        assertThat(faker.zelda().character()).matches("(?U)([\\w'\\-.()]+ ?)+");
    }
}
