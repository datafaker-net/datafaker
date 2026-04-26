package net.datafaker.providers.base;

import de.speedbanking.iban.IbanRegistry;
import de.speedbanking.iban.RandomIban;
import net.datafaker.annotations.Deterministic;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @since 0.8.0
 */
public class Finance extends AbstractProvider<BaseProviders> {

    public static final BigInteger A_CODE = BigInteger.valueOf(97L);

    public enum CreditCardType {
        VISA,
        MASTERCARD,
        DISCOVER,
        AMERICAN_EXPRESS,
        DINERS_CLUB,
        JCB,
        DANKORT,
        FORBRUGSFORENINGEN,
        LASER,
        UNIONPAY
    }

    protected Finance(BaseProviders faker) {
        super(faker);
    }

    /**
     * @deprecated since 2.2.0. For removal in 3.0.0 version. Use {@link Stock#nsdqSymbol} instead.
     */
    @Deprecated(since = "2.2.0", forRemoval = true)
    public String nasdaqTicker() {
        return resolve("finance.ticker.nasdaq");
    }

    /**
     * @deprecated since 2.2.0. For removal in 3.0.0 version. Use {@link Stock#nyseSymbol} instead.
     */
    @Deprecated(since = "2.2.0", forRemoval = true)
    public String nyseTicker() {
        return resolve("finance.ticker.nyse");
    }

    /**
     * @deprecated since 2.2.0. For removal in 3.0.0 version. Use {@link Stock#exchanges} instead.
     */
    @Deprecated(since = "2.2.0", forRemoval = true)
    public String stockMarket() {
        return resolve("finance.stock_market");
    }

    /** Get the set of country codes supported for IBAN generation */
    @Deterministic
    public static Set<String> ibanSupportedCountries() {
        return Arrays.stream(IbanRegistry.values())
            .map(IbanRegistry::getCountryCode)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Generates a random credit card number of the specified type.
     * The number will pass the LUHN check.
     *
     * @param creditCardType the type of credit card to generate (see {@link CreditCardType} for options)
     * @return a valid credit card number of the specified type
     */
    public String creditCard(CreditCardType creditCardType) {
        final String key = "finance.credit_card." + creditCardType.toString().toLowerCase(Locale.ROOT);
        String value = resolve(key);
        final String template = faker.numerify(value);

        int[] digits = template.chars().filter(Character::isDigit).boxed().mapToInt(t -> t - '0').toArray();
        int luhnSum = 0;
        int multiplier = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            multiplier = (multiplier == 2 ? 1 : 2);
            luhnSum += sumOfDigits(digits[i] * multiplier);
        }
        int luhnDigit = (10 - (luhnSum % 10)) % 10;
        StringBuilder res = new StringBuilder(template.length());
        for (int i = 0; i < template.length(); i++) {
            final char c = template.charAt(i);
            switch (c) {
                case '/', '\\' -> {if (!res.isEmpty() && i != template.length() - 1) {res.append(' ');}}
                case 'L' -> res.append(luhnDigit);
                default -> res.append(c);
            }
        }
        return res.toString().trim();
    }

    private int sumOfDigits(int value) {
        int res = 0;
        while (value > 0) {
            res += value % 10;
            value /= 10;
        }
        return res;
    }

    /**
     * Generate a random credit card number of a random type.
     * The number will pass the LUHN check.
     *
     * @return a valid credit card number of a random type. Likely 16 digits, but could be 14-19.
     */
    public String creditCard() {
        CreditCardType type = randomCreditCardType();
        return creditCard(type);
    }

    /**
     * Generates a random Business Identifier Code
     */
    public String bic() {
        return faker.regexify("([A-Z]){4}([A-Z]){2}([0-9A-Z]){2}([0-9A-Z]{3})?");
    }

    /**
     * Generates a random IBAN (International Bank Account Number).
     * The country code is chosen randomly from the supported countries.
     * The generated IBAN will pass the checksum test.
     * @return a valid IBAN
     */
    public String iban() {
        String countryCode = faker.options().nextElement(IbanRegistry.values()).getCountryCode();
        return iban(countryCode);
    }

    /**
     * Generates an IBAN for the given country code.
     * The generated IBAN will pass the checksum test.
     * @param countryCode the 2-letter country code for the IBAN.
     * @return a valid IBAN
     */
    public String iban(String countryCode) {
        return RandomIban.of(countryCode).toString();
    }

    /**
     * Generates a random US bank routing number of 9 characters.
     * The number will pass the checksum test.
     * @return a valid US bank routing number
     */
    public String usRoutingNumber() {
        final int random = faker.random().nextInt(12) + 1;
        final String base =
            // 01 through 12 are the "normal" routing numbers, and correspond to the 12 Federal Reserve Banks.
            (random < 10 ? "0" : "") + random
            + faker.numerify("#".repeat(6));
        int check =
           Character.getNumericValue(base.charAt(0)) * 3
            + Character.getNumericValue(base.charAt(1)) * 7
            + Character.getNumericValue(base.charAt(2))
            + Character.getNumericValue(base.charAt(3)) * 3
            + Character.getNumericValue(base.charAt(4)) * 7
            + Character.getNumericValue(base.charAt(5))
            + Character.getNumericValue(base.charAt(6)) * 3
            + Character.getNumericValue(base.charAt(7)) * 7;
        check = Math.abs(check % 10 - 10) % 10;

        return base + check;
    }

    private CreditCardType randomCreditCardType() {
        return faker.random().nextEnum(CreditCardType.class);
    }
}
