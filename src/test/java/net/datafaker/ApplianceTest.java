package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApplianceTest extends AbstractFakerTest {

    @Test
    void brand() {
        assertThat(faker.appliance().brand()).matches("[A-Za-z .-]+");
    }

    @Test
    void equipment() {
        assertThat(faker.appliance().equipment()).isNotEmpty();
    }
}
