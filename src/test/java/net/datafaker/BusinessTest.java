package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessTest extends AbstractFakerTest {
    @Test
    void creditCardNumber() {
        assertThat(faker.business().creditCardNumber()).isNotEmpty();
    }

    @Test
    void creditCardType() {
        assertThat(faker.business().creditCardType()).isNotEmpty();
    }

    @Test
    void creditCardExpiry() {
        assertThat(faker.business().creditCardExpiry()).isNotEmpty();
    }

}
