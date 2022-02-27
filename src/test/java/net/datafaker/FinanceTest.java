package net.datafaker;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FinanceTest extends AbstractFakerTest {

    @RepeatedTest(100)
    public void creditCard() {
        final String creditCard = faker.finance().creditCard();
        assertCardLuhnDigit(creditCard);
    }

    private void assertCardLuhnDigit(String creditCard) {
        final String creditCardStripped = creditCard.replaceAll("-", "");
        assertThat(LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(creditCardStripped), is(true));
    }

    @RepeatedTest(10)
    public void nasdaqTicker() {
        assertThat(faker.finance().nasdaqTicker(), matchesRegularExpression("[A-Z.-]+"));
    }

    @RepeatedTest(10)
    public void nyseTicker() {
        assertThat(faker.finance().nyseTicker(), matchesRegularExpression("[A-Z.-]+"));
    }

    @Test
    public void stockMarket() {
        assertThat(faker.finance().stockMarket(), matchesRegularExpression("[A-Z.-]+"));
    }


    @Test
    public void bic() {
        assertThat(faker.finance().bic(), matchesRegularExpression("([A-Z]){4}([A-Z]){2}([0-9A-Z]){2}([0-9A-Z]{3})?"));
    }

    @RepeatedTest(100)
    public void iban() {
        assertThat(faker.finance().iban(), matchesRegularExpression("[A-Z]{2}\\p{Alnum}{13,30}"));
    }

    @Test
    public void ibanWithCountryCode() {
        assertThat(faker.finance().iban("DE"), matchesRegularExpression("DE\\d{20}"));
    }

    @Test
    public void creditCardWithType() {
        for (CreditCardType type : CreditCardType.values()) {
            final String creditCard = faker.finance().creditCard(type);
            assertCardLuhnDigit(creditCard);
        }
    }

    @Test
    public void costaRicaIbanMustBeValid() {
        final String givenCountryCode = "CR";
        final Faker faker = new Faker();
        final String ibanFaker = faker.finance().iban(givenCountryCode).toUpperCase(faker.getLocale());
        assertTrue(fr.marcwrobel.jbanking.iban.Iban.isValid(ibanFaker));
    }
}
