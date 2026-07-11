package net.datafaker.providers.base;

import static org.assertj.core.api.Assertions.assertThat;

import fr.marcwrobel.jbanking.bic.Bic;
import fr.marcwrobel.jbanking.checkdigit.IbanCheckDigit;
import fr.marcwrobel.jbanking.iban.BbanStructure;
import fr.marcwrobel.jbanking.iban.Iban;
import net.datafaker.providers.base.Finance.CreditCardType;
import org.apache.commons.validator.routines.CreditCardValidator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class FinanceTest extends BaseFakerTest {

    private static final Set<String> JBANKING_SUPPORTED_COUNTRY_CODES = Arrays.stream(BbanStructure.values())
        .map(Enum::name)
        .collect(Collectors.toUnmodifiableSet());
    private static final CreditCardValidator CREDIT_CARD_VALIDATOR = CreditCardValidator.genericCreditCardValidator();

    private final Finance finance = faker.finance();

    @RepeatedTest(100)
    void creditCard() {
        assertThatCreditCardIsValid(finance.creditCard().replace("-", ""), null, CREDIT_CARD_VALIDATOR);
    }

    @RepeatedTest(10)
    @SuppressWarnings("removal")
    void nasdaqTicker() {
        assertThat(finance.nasdaqTicker()).matches("[A-Z.-]+");
    }

    @RepeatedTest(10)
    @SuppressWarnings("removal")
    void nyseTicker() {
        assertThat(finance.nyseTicker()).matches("[A-Z.-]+");
    }

    @Override
    @SuppressWarnings("removal")
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(finance::stockMarket, "finance.stock_market"));
    }

    @RepeatedTest(100)
    void bic() {
        String bic = finance.bic();
        assertThat(Bic.isValid(bic)).as("BIC '%s' should be valid", bic).isTrue();
    }

    @RepeatedTest(100)
    void iban() {
        String iban = finance.iban();
        assertIbanIsValid(iban, iban.substring(0, 2));
        assertThat(Finance.ibanSupportedCountries()).contains(iban.substring(0, 2));
    }

    @Test
    void ibanWithCountryCode() {
        String ibanDe = finance.iban("DE");
        assertIbanIsValid(ibanDe, "DE");
        assertThat(ibanDe).matches("DE\\d{20}");
    }

    @Test
    void ibanCountryCodes() {
        assertThat(Finance.ibanSupportedCountries()).isNotEmpty().hasSizeGreaterThan(100);
    }

    @Test
    void ibanWithAllCountryCodes() {
        Set<String> ibanCountryCodes = Finance.ibanSupportedCountries();
        for (var givenCountryCode : ibanCountryCodes) {
            final String iban = finance.iban(givenCountryCode).toUpperCase(faker.getContext().getLocale());
            assertIbanIsValid(iban, givenCountryCode);
        }
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @EnumSource(CreditCardType.class)
    void creditCardWithType(CreditCardType type) {
        String cc = finance.creditCard(type).replace("-", "");
        assertThatCreditCardIsValid(cc, type, CREDIT_CARD_VALIDATOR);
    }

    @Test
    void costaRicaIbanMustBeValid() {
        final String givenCountryCode = "CR";
        final BaseFaker faker = new BaseFaker();
        final String ibanFaker = finance.iban(givenCountryCode).toUpperCase(faker.getContext().getLocale());
        assertIbanIsValid(ibanFaker, givenCountryCode);
    }

    private static void assertIbanIsValid(String iban, String expectedCountryCode) {
        assertThat(iban).startsWith(expectedCountryCode);
        assertThat(IbanCheckDigit.INSTANCE.validate(iban))
            .as("IBAN '%s' should have valid check digits", iban)
            .isTrue();

        if (JBANKING_SUPPORTED_COUNTRY_CODES.contains(expectedCountryCode)) {
            assertThat(Iban.isValid(iban)).as("IBAN '%s' should be valid", iban).isTrue();
            assertThat(new Iban(iban).getCountryCode()).isEqualTo(expectedCountryCode);
        }
    }

    @RepeatedTest(100)
    void visaCard() {
        String creditCard = finance.creditCard(CreditCardType.VISA).replace("-", "");
        assertThat(creditCard).startsWith("4").hasSize(16);
        assertThatCreditCardIsValid(creditCard, CreditCardType.VISA, CREDIT_CARD_VALIDATOR);
    }

    @RepeatedTest(100)
    void discoverCard() {
        String creditCard = finance.creditCard(CreditCardType.DISCOVER).replace("-", "");
        assertThat(creditCard).startsWith("6").hasSize(16);
        assertThatCreditCardIsValid(creditCard, CreditCardType.DISCOVER, CREDIT_CARD_VALIDATOR);
    }

    @RepeatedTest(100)
    void unionpayCard() {
        List<String> startingDigits = List.of("62", "64", "65", "81");
        String creditCard = finance.creditCard(CreditCardType.UNIONPAY).replace("-", "");
        assertThatCreditCardIsValid(creditCard, CreditCardType.UNIONPAY, CreditCardValidator.genericCreditCardValidator(16));
        assertThat(startingDigits)
            .as("UnionPay card '%s' should start with one of %s", creditCard, startingDigits)
            .anyMatch(prefix -> creditCard.startsWith(prefix));
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

    /**
     * Asserts that the given credit card number is valid according to the specified validator.
     *
     * @param creditCard the card number to validate
     * @param type the expected card type, or {@code null} if unspecified
     * @param creditCardValidator the validator instance used for verification
     */
    private void assertThatCreditCardIsValid(String creditCard, CreditCardType type, CreditCardValidator creditCardValidator) {
        assertThat(creditCard)
            .satisfies(cc -> {
                assertThat(creditCardValidator.isValid(cc))
                    .as("Credit card %s (%s) should be valid according to %s",
                        cc, Objects.requireNonNullElse(type, "unspecified"), creditCardValidator.getClass().getName())
                    .isTrue();
            });
    }

}
