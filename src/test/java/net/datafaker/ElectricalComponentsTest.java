package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ElectricalComponentsTest extends AbstractFakerTest {

    @Test
    void testActive() {
        String activeComponent = faker.electricalComponents().active();
        assertThat(activeComponent).isNotEmpty();
    }

    @Test
    void testPassive() {
        String passiveComponent = faker.electricalComponents().passive();
        assertThat(passiveComponent).isNotEmpty();
    }

    @Test
    void testElectromechanical() {
        String electromechanicalComponent = faker.electricalComponents().electromechanical();
        assertThat(electromechanicalComponent).isNotEmpty();
    }
}
