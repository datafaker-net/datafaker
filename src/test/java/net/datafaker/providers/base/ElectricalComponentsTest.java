package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ElectricalComponentsTest extends BaseFakerTest<BaseFaker> {

    @Test
    void active() {
        String activeComponent = faker.electricalComponents().active();
        assertThat(activeComponent).isNotEmpty();
    }

    @Test
    void passive() {
        String passiveComponent = faker.electricalComponents().passive();
        assertThat(passiveComponent).isNotEmpty();
    }

    @Test
    void electromechanical() {
        String electromechanicalComponent = faker.electricalComponents().electromechanical();
        assertThat(electromechanicalComponent).isNotEmpty();
    }
}
