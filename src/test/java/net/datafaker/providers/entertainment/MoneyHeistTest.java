package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyHeistTest extends EntertainmentFakerTest {

    @Test
    void character() {
        assertThat(faker.moneyHeist().character()).isNotEmpty();
    }

    @Test
    void heist() {
        assertThat(faker.moneyHeist().heist()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.moneyHeist().quote()).isNotEmpty();
    }
}
