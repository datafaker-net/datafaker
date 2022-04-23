package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTest extends AbstractFakerTest {

    @RepeatedTest(10)
    public void testFlag() {
        String flag = faker.country().flag();
        assertThat(flag).matches("^https://flags.fmcdn\\.net/data/flags/w580/[a-zA-Z0-9_]+\\.png$");
    }


    @Test
    public void testCode2() {
        assertThat(faker.country().countryCode2()).matches("([a-z]{2})");
    }

    @Test
    public void testCode3() {
        assertThat(faker.country().countryCode3()).matches("([a-z]{3})");
    }

    @Test
    public void testCapital() {
        assertThat(faker.country().capital()).matches("([\\p{L}0-9+,. '-])+");
    }

    @Test
    public void testCurrency() {
        assertThat(faker.country().currency()).matches("([A-Za-zÀ-ÿ'’()-]+ ?)+");
    }

    @Test
    public void testCurrencyCode() {
        assertThat(faker.country().currencyCode()).matches("([\\w-’í]+ ?)+");
    }

    @Test
    public void testName() {
        assertThat(faker.country().name()).isNotEmpty();
    }
}
