package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AquaTeenHungerForceTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.aquaTeenHungerForce().character()).matches("[A-Za-z .]+");
    }
}
