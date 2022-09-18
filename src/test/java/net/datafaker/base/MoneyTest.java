package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.isNullOrEmpty;

class MoneyTest extends AbstractBaseFakerTest {

    @Test
    void testCurrency() {
        assertThat(isNullOrEmpty(faker.money().currency())).isFalse();
    }

    @Test
    void testCurrencyCode() {
        assertThat(isNullOrEmpty(faker.money().currencyCode())).isFalse();
    }
}
