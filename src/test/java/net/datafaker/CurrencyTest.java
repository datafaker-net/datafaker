package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyTest extends AbstractFakerTest {

    @Test
    public void testName() {
        assertThat(faker.currency().name()).matches("[\\w'.\\-() ]+");
    }

    @Test
    public void testCode() {
        final Currency currency = faker.currency();
        assertThat(currency.code()).matches("[A-Z]{3}");
    }
}
