package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyTest extends BaseFakerTest<BaseFaker> {

    @Test
    void name() {
        assertThat(faker.currency().name()).matches("[\\w'.\\-() ]+");
    }

    @Test
    void code() {
        final Currency currency = faker.currency();
        assertThat(currency.code()).matches("[A-Z]{3}");
    }
}
