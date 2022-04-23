package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BusinessTest extends AbstractFakerTest {
    @Test
    public void creditCardNumber() {
        assertThat(faker.business().creditCardNumber()).isNotEmpty();
    }

    @Test
    public void creditCardType() {
        assertThat(faker.business().creditCardType()).isNotEmpty();
    }

    @Test
    public void creditCardExpiry() {
        assertThat(faker.business().creditCardExpiry()).isNotEmpty();
    }

}
