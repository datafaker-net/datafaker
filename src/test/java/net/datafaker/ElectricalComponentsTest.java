package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElectricalComponentsTest extends AbstractFakerTest{

    @Test
    public void testActive() {
        String activeComponent = faker.electricalComponents().active();
        assertThat(activeComponent).isNotEmpty();
    }

    @Test
    public void testPassive() {
        String passiveComponent = faker.electricalComponents().passive();
        assertThat(passiveComponent).isNotEmpty();
    }

    @Test
    public void testElectromechanical() {
        String electromechanicalComponent = faker.electricalComponents().electromechanical();
        assertThat(electromechanicalComponent).isNotEmpty();
    }
}
