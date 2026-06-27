package net.datafaker.providers.base;

import static de.speedbanking.bic.junit.jupiter.api.BicAssertions.assertThatBicIsValid;
import static de.speedbanking.iban.junit.jupiter.api.IbanAssertions.assertThatIban;
import static de.speedbanking.iban.junit.jupiter.api.IbanAssertions.assertThatIbanIsValid;
import static org.assertj.core.api.Assertions.assertThat;

import de.speedbanking.iban.Iban;
import de.speedbanking.iban.IbanRegistry;
import net.datafaker.junit.FakerSource;
import net.datafaker.junit.FakerSource.Param;
import net.datafaker.providers.base.Finance.CreditCardType;
import org.apache.commons.validator.routines.CreditCardValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

class FinanceTest extends BaseFakerTest {

    private static final CreditCardValidator CREDIT_CARD_VALIDATOR = CreditCardValidator.genericCreditCardValidator();

    private final Finance finance = faker.finance();

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "finance#creditCard()", repeat = 100)
    void creditCard(String creditCard) {
        assertThatCreditCardIsValid(creditCard.replace("-", ""), null, CREDIT_CARD_VALIDATOR);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "finance#nasdaqTicker", repeat = 10)
    void nasdaqTicker(CharSequence ticker) {
        assertThat(ticker).matches("[A-Z.-]+");
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "finance#nyseTicker", repeat = 10)
    void nyseTicker(String ticker) {
        assertThat(ticker).matches("[A-Z.-]+");
    }

    @Override
    @SuppressWarnings("removal")
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(finance::stockMarket, "finance.stock_market"));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "finance#bic", repeat = 100)
    void bic(String bic) {
        assertThatBicIsValid(bic);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "finance#iban", repeat = 100)
    void iban(String iban) {
        assertThatIbanIsValid(iban);
        assertThat(Finance.ibanSupportedCountries()).contains(Iban.of(iban).getCountryCode());
    }

    @ParameterizedTest(name = "[{index}] {1}: {0}")
    @FakerSource(code = "finance#iban(String)", multiParams = @Param({"IR", "LB", "PL"}), repeat = 5)
    void ibanWithCountryCode(CharSequence iban, String countryCode) {
        assertThatIban(iban.toString())
            .hasCountryCode(countryCode)
            .hasLength(IbanRegistry.getByCode(countryCode).getIbanLength());
    }

    @Test
    void ibanCountryCodes() {
        assertThat(Finance.ibanSupportedCountries()).isNotEmpty().hasSizeGreaterThan(100);
        Finance.ibanSupportedCountries().forEach(c ->
            assertThat(IbanRegistry.getByCode(c))
                .as("IbanRegistry should contain entry for country code '%s'", c)
                .isNotNull());
    }

    @Test
    void ibanWithAllCountryCodes() {
        Set<String> ibanCountryCodes = Finance.ibanSupportedCountries();
        for (var givenCountryCode : ibanCountryCodes) {
            final String iban = finance.iban(givenCountryCode).toUpperCase(faker.getContext().getLocale());
            assertThatIban(iban)
                .hasCountryCode(givenCountryCode)
                .hasLength(IbanRegistry.getByCode(givenCountryCode).getIbanLength());
        }
    }

    @ParameterizedTest(name = "[{index}] {1}: {0}")
    @FakerSource(code = "finance#creditCard(CreditCardType)", repeat = 10)
    void creditCardWithType(String creditCard, CreditCardType type) {
        assertThatCreditCardIsValid(creditCard.replace("-", ""), type, CREDIT_CARD_VALIDATOR);
    }

    @Test
    void costaRicaIbanMustBeValid() {
        final String givenCountryCode = "CR";
        final BaseFaker faker = new BaseFaker();
        final String ibanFaker = finance.iban(givenCountryCode).toUpperCase(faker.getContext().getLocale());
        assertThatIban(ibanFaker)
            .hasCountryCode(givenCountryCode)
            .hasLength(IbanRegistry.CR.getIbanLength());
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "finance#creditCard(CreditCardType)", params = "VISA", repeat = 100)
    void visaCard(String creditCard) {
        String digits = creditCard.replace("-", "");
        assertThat(digits).startsWith("4").hasSize(16);
        assertThatCreditCardIsValid(digits, CreditCardType.VISA, CREDIT_CARD_VALIDATOR);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "finance#creditCard(CreditCardType)", params = "DISCOVER", repeat = 100)
    void discoverCard(String creditCard) {
        String digits = creditCard.replace("-", "");
        assertThat(digits).startsWith("6").hasSize(16);
        assertThatCreditCardIsValid(digits, CreditCardType.DISCOVER, CREDIT_CARD_VALIDATOR);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "finance#creditCard(CreditCardType)", params = "UNIONPAY", repeat = 100)
    void unionpayCard(String creditCard) {
        List<String> startingDigits = List.of("62", "64", "65", "81");
        String digits = creditCard.replace("-", "");
        assertThatCreditCardIsValid(digits, CreditCardType.UNIONPAY, CreditCardValidator.genericCreditCardValidator(16));
        assertThat(startingDigits)
            .as("UnionPay card '%s' should start with one of %s", digits, startingDigits)
            .anyMatch(prefix -> digits.startsWith(prefix));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @FakerSource(code = "finance#usRoutingNumber", repeat = 100)
    void usRoutingNumber(String rtn) {
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

    /**
     * Asserts that the given credit card number is valid according to the specified validator.
     *
     * @param creditCard the card number to validate
     * @param creditCardType the expected card type, or {@code null} if unspecified
     * @param creditCardValidator the validator instance used for verification
     */
    private void assertThatCreditCardIsValid(String creditCard, CreditCardType creditCardType, CreditCardValidator creditCardValidator) {
        assertThat(creditCardValidator).isNotNull();
        assertThat(creditCard)
            .satisfies(cc -> {
                assertThat(creditCardValidator.isValid(cc))
                    .as("Credit card %s (%s) should be valid according to %s",
                        cc, Objects.requireNonNullElse(creditCardType, "unspecified"), creditCardValidator.getClass().getName())
                    .isTrue();
            });
    }

}
