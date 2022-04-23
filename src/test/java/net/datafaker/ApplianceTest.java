package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplianceTest extends AbstractFakerTest {

    @Test
    public void brand() {
        assertThat(faker.appliance().brand()).matches("[A-Za-z .-]+");
    }

    @Test
    public void equipment() {
        assertThat(faker.appliance().equipment()).isNotEmpty();
    }
}
