package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testCurrency() {
        assertThat(faker.money().currency()).isNotEmpty();
    }

    @Test
    void testCurrencyCode() {
        assertThat(faker.money().currencyCode()).isNotEmpty();
    }

    @Test
    void testNumericCode() {
        assertThat(faker.money().currencyNumericCode()).isNotEmpty();
    }

    @Test
    void testCurrencySymbol() {
        assertThat(faker.money().currencySymbol()).isNotEmpty();
    }

}
