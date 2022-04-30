package net.datafaker;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FinanceTest extends AbstractFakerTest {

    @RepeatedTest(100)
    void creditCard() {
        final String creditCard = faker.finance().creditCard();
        assertCardLuhnDigit(creditCard);
    }

    private void assertCardLuhnDigit(String creditCard) {
        final String creditCardStripped = creditCard.replaceAll("-", "");
        assertThat(LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(creditCardStripped)).isTrue();
    }

    @RepeatedTest(10)
    void nasdaqTicker() {
        assertThat(faker.finance().nasdaqTicker()).matches("[A-Z.-]+");
    }

    @RepeatedTest(10)
    void nyseTicker() {
        assertThat(faker.finance().nyseTicker()).matches("[A-Z.-]+");
    }

    @Test
    void stockMarket() {
        assertThat(faker.finance().stockMarket()).matches("[A-Z.-]+");
    }


    @Test
    void bic() {
        assertThat(faker.finance().bic()).matches("([A-Z]){4}([A-Z]){2}([0-9A-Z]){2}([0-9A-Z]{3})?");
    }

    @RepeatedTest(100)
    void iban() {
        assertThat(faker.finance().iban()).matches("[A-Z]{2}\\p{Alnum}{13,30}");
    }

    @Test
    void ibanWithCountryCode() {
        assertThat(faker.finance().iban("DE")).matches("DE\\d{20}");
    }

    @Test
    void creditCardWithType() {
        for (CreditCardType type : CreditCardType.values()) {
            final String creditCard = faker.finance().creditCard(type);
            assertCardLuhnDigit(creditCard);
        }
    }

    @Test
    void costaRicaIbanMustBeValid() {
        final String givenCountryCode = "CR";
        final Faker faker = new Faker();
        final String ibanFaker = faker.finance().iban(givenCountryCode).toUpperCase(faker.getLocale());
        assertThat(fr.marcwrobel.jbanking.iban.Iban.isValid(ibanFaker)).isTrue();
    }
}
