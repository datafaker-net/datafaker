package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApplianceTest extends BaseFakerTest {

    @Test
    void brand() {
        assertThat(faker.appliance().brand()).matches("[A-Za-z .-]+");
    }

    @Test
    void equipment() {
        assertThat(faker.appliance().equipment()).isNotEmpty();
    }
}
