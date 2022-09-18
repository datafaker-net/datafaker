package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AquaTeenHungerForceTest extends AbstractBaseFakerTest {

    @Test
    void character() {
        assertThat(faker.aquaTeenHungerForce().character()).matches("[A-Za-z .]+");
    }
}
