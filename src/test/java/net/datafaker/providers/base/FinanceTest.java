package net.datafaker.providers.base;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static net.datafaker.providers.base.Finance.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

class FinanceTest extends BaseFakerTest {

    private final Finance finance = faker.finance();

    @RepeatedTest(100)
    void creditCard() {
        final String creditCard = finance.creditCard();
        assertCardLuhnDigit(creditCard);
    }

    private void assertCardLuhnDigit(String creditCard) {
        final String creditCardStripped = creditCard.replace("-", "");
        assertThat(LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(creditCardStripped)).isTrue();
    }

    @RepeatedTest(10)
    void nasdaqTicker() {
        assertThat(finance.nasdaqTicker()).matches("[A-Z.-]+");
    }

    @RepeatedTest(10)
    void nyseTicker() {
        assertThat(finance.nyseTicker()).matches("[A-Z.-]+");
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(finance::stockMarket, "finance.stock_market"));
    }

    @Test
    void bic() {
        assertThat(finance.bic()).matches("([A-Z]){4}([A-Z]){2}([0-9A-Z]){2}([0-9A-Z]{3})?");
    }

    @RepeatedTest(100)
    void iban() {
        assertThat(finance.iban()).matches("[A-Z]{2}[A-Z0-9]{13,31}");
    }

    @Test
    void ibanWithCountryCode() {
        assertThat(finance.iban("DE")).matches("DE\\d{20}");
    }

    @Test
    void ibanCountryCodes() {
        assertThat(Finance.ibanSupportedCountries()).isNotEmpty().hasSizeGreaterThan(70);
    }

    @Test
    void ibanWithAllCountryCodes() {
        Set<String> ibanCountryCodes = Finance.ibanSupportedCountries();
        for (String givenCountryCode : ibanCountryCodes) {
            final String iban = finance.iban(givenCountryCode).toUpperCase(faker.getContext().getLocale());
            assertThat(iban).isNotBlank();
        }
    }

    @Test
    void creditCardWithType() {
        for (CreditCardType type : CreditCardType.values()) {
            final String creditCard = finance.creditCard(type);
            assertCardLuhnDigit(creditCard);
        }
    }

    @Test
    void costaRicaIbanMustBeValid() {
        final String givenCountryCode = "CR";
        final BaseFaker faker = new BaseFaker();
        final String ibanFaker = finance.iban(givenCountryCode).toUpperCase(faker.getContext().getLocale());
        assertThat(fr.marcwrobel.jbanking.iban.Iban.isValid(ibanFaker)).isTrue();
    }

    @RepeatedTest(100)
    void visaCard() {
        String creditCard = finance.creditCard(CreditCardType.VISA).replace("-", "");
        assertThat(creditCard).startsWith("4").hasSize(16);
    }

    @RepeatedTest(100)
    void discoverCard() {
        String creditCard = finance.creditCard(CreditCardType.DISCOVER).replace("-", "");
        assertThat(creditCard).startsWith("6").hasSize(16);
    }

    @RepeatedTest(100)
    void unionpayCard() {
        String[] startingDigits = {"62", "64", "65", "81"};
        String creditCard = finance.creditCard(CreditCardType.UNIONPAY).replace("-", "");
        assertThat(creditCard).hasSize(16);
        assertThat(Stream.of(startingDigits).anyMatch(creditCard::startsWith))
            .as(() -> "Expected UnionPay credit card number to begin with the correct digits (" + String.join(", ", startingDigits) + "), but received: " + creditCard)
            .isTrue();
    }

    @RepeatedTest(100)
    void usRoutingNumber() {
        String rtn = finance.usRoutingNumber();
        assertThat(rtn).matches("\\d{9}");
        int check = 0;
        for (int index = 0; index < 3; index++) {
            final int pos = index * 3;
            check += Character.getNumericValue(rtn.charAt(pos)) * 3;
            check += Character.getNumericValue(rtn.charAt(pos + 1)) * 7;
            check += Character.getNumericValue(rtn.charAt(pos + 2));
        }
        assertThat(check % 10).isZero();
    }
}
