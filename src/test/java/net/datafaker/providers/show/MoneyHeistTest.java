package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyHeistTest extends ShowFakerTest {

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
