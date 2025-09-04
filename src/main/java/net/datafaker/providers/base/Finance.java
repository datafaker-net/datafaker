package net.datafaker.providers.base;

import net.datafaker.annotations.Deterministic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
        SWITCH,
        SOLO,
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

    private static final Map<String, String> countryCodeToBasicBankAccountNumberPattern =
        createCountryCodeToBasicBankAccountNumberPatternMap();

    /** Get the set of country codes supported for IBAN generation */
    @Deterministic
    public static Set<String> ibanSupportedCountries() {
        return countryCodeToBasicBankAccountNumberPattern.keySet();
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
        List<String> countryCodes = new ArrayList<>(countryCodeToBasicBankAccountNumberPattern.keySet());
        String randomCountryCode = countryCodes.get(faker.random().nextInt(countryCodes.size()));
        return iban(randomCountryCode);
    }

    /**
     * Generates an IBAN for the given country code.
     * The generated IBAN will pass the checksum test.
     * @param countryCode the 2-letter country code for the IBAN.
     * @return a valid IBAN
     */
    public String iban(String countryCode) {
        String basicBankAccountNumber = faker.regexify(countryCodeToBasicBankAccountNumberPattern.get(countryCode));
        String checkSum = calculateIbanChecksum(countryCode, basicBankAccountNumber);
        return countryCode + checkSum + basicBankAccountNumber;
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

    private static String calculateIbanChecksum(String countryCode, String basicBankAccountNumber) {
        String basis = (basicBankAccountNumber + countryCode).toLowerCase(Locale.ROOT) + "00";

        final StringBuilder sb = new StringBuilder(basis.length());
        for (int i = 0; i < basis.length(); i++) {
            final char c = basis.charAt(i);
            if (Character.isLetter(c)) {
                sb.append((c - 'a') + 10);
            } else {
                sb.append(c);
            }
        }

        int mod97 = new BigInteger(sb.toString()).mod(A_CODE).intValue();
        return padLeftZeros(String.valueOf(98 - mod97), 2);
    }

    private static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder(length);
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }

    private static Map<String, String> createCountryCodeToBasicBankAccountNumberPatternMap() {
        // source: https://www.swift.com/standards/data-standards/iban
        // version 87
        Map<String, String> ibanFormats = new HashMap<>();
        ibanFormats.put("AD", "\\d{4}\\d{4}[0-9A-Z]{12}");
        ibanFormats.put("AE", "\\d{3}\\d{16}");
        ibanFormats.put("AL", "\\d{8}[0-9A-Z]{16}");
        ibanFormats.put("AT", "\\d{5}\\d{11}");
        ibanFormats.put("AZ", "[A-Z]{4}[0-9A-Z]{20}");
        ibanFormats.put("BA", "\\d{3}\\d{3}\\d{8}\\d{2}");
        ibanFormats.put("BE", "\\d{3}\\d{7}\\d{2}");
        ibanFormats.put("BG", "[A-Z]{4}\\d{4}\\d{2}[0-9A-Z]{8}");
        ibanFormats.put("BH", "[A-Z]{4}[0-9A-Z]{14}");
        ibanFormats.put("BR", "\\d{8}\\d{5}\\d{10}[A-Z]{1}[0-9A-Z]{1}");
        ibanFormats.put("BY", "[0-9A-Z]{4}\\d{4}[0-9A-Z]{16}");
        ibanFormats.put("CH", "\\d{5}[0-9A-Z]{12}");
        ibanFormats.put("CR", "0\\d{3}\\d{14}");
        ibanFormats.put("CY", "\\d{3}\\d{5}[0-9A-Z]{16}");
        ibanFormats.put("CZ", "\\d{4}\\d{6}\\d{10}");
        ibanFormats.put("DE", "\\d{8}\\d{10}");
        ibanFormats.put("DK", "\\d{4}\\d{9}\\d{1}");
        ibanFormats.put("DO", "[0-9A-Z]{4}\\d{20}");
        ibanFormats.put("EE", "\\d{2}\\d{2}\\d{11}\\d{1}");
        ibanFormats.put("EG", "\\d{4}\\d{4}\\d{17}");
        ibanFormats.put("ES", "\\d{4}\\d{4}\\d{1}\\d{1}\\d{10}");
        ibanFormats.put("FI", "\\d{6}\\d{7}\\d{1}");
        ibanFormats.put("FO", "\\d{4}\\d{9}\\d{1}");
        ibanFormats.put("FR", "\\d{5}\\d{5}[0-9A-Z]{11}\\d{2}");
        ibanFormats.put("GB", "[A-Z]{4}\\d{6}\\d{8}");
        ibanFormats.put("GE", "[A-Z]{2}\\d{16}");
        ibanFormats.put("GI", "[A-Z]{4}[0-9A-Z]{15}");
        ibanFormats.put("GL", "\\d{4}\\d{9}\\d{1}");
        ibanFormats.put("GR", "\\d{3}\\d{4}[0-9A-Z]{16}");
        ibanFormats.put("GT", "[0-9A-Z]{4}[0-9A-Z]{20}");
        ibanFormats.put("HR", "\\d{7}\\d{10}");
        ibanFormats.put("HU", "\\d{3}\\d{4}\\d{1}\\d{15}\\d{1}");
        ibanFormats.put("IE", "[A-Z]{4}\\d{6}\\d{8}");
        ibanFormats.put("IL", "\\d{3}\\d{3}\\d{13}");
        ibanFormats.put("IQ", "[A-Z]{4}\\d{3}\\d{12}");
        ibanFormats.put("IS", "\\d{4}\\d{2}\\d{6}\\d{10}");
        ibanFormats.put("IT", "[A-Z]{1}\\d{5}\\d{5}[0-9A-Z]{12}");
        ibanFormats.put("JO", "[A-Z]{4}\\d{4}[0-9A-Z]{18}");
        ibanFormats.put("KW", "[A-Z]{4}[0-9A-Z]{22}");
        ibanFormats.put("KZ", "\\d{3}[0-9A-Z]{13}");
        ibanFormats.put("LB", "\\d{4}[0-9A-Z]{20}");
        ibanFormats.put("LC", "[A-Z]{4}[0-9A-Z]{24}");
        ibanFormats.put("LI", "\\d{5}[0-9A-Z]{12}");
        ibanFormats.put("LT", "\\d{5}\\d{11}");
        ibanFormats.put("LU", "\\d{3}[0-9A-Z]{13}");
        ibanFormats.put("LV", "[A-Z]{4}[0-9A-Z]{13}");
        ibanFormats.put("MC", "\\d{5}\\d{5}[0-9A-Z]{11}\\d{2}");
        ibanFormats.put("MD", "[0-9A-Z]{2}[0-9A-Z]{18}");
        ibanFormats.put("ME", "\\d{3}\\d{13}\\d{2}");
        ibanFormats.put("MK", "\\d{3}[0-9A-Z]{10}\\d{2}");
        ibanFormats.put("MR", "\\d{5}\\d{5}\\d{11}\\d{2}");
        ibanFormats.put("MT", "[A-Z]{4}\\d{5}[0-9A-Z]{18}");
        ibanFormats.put("MU", "[A-Z]{4}\\d{2}\\d{2}\\d{12}\\d{3}[A-Z]{3}");
        ibanFormats.put("NL", "[A-Z]{4}\\d{10}");
        ibanFormats.put("NO", "\\d{4}\\d{6}\\d{1}");
        ibanFormats.put("PK", "[A-Z]{4}[0-9A-Z]{16}");
        ibanFormats.put("PL", "\\d{8}\\d{16}");
        ibanFormats.put("PS", "[A-Z]{4}[0-9A-Z]{21}");
        ibanFormats.put("PT", "\\d{4}\\d{4}\\d{11}\\d{2}");
        ibanFormats.put("QA", "[A-Z]{4}[0-9A-Z]{21}");
        ibanFormats.put("RO", "[A-Z]{4}[0-9A-Z]{16}");
        ibanFormats.put("RS", "\\d{3}\\d{13}\\d{2}");
        ibanFormats.put("SA", "\\d{2}[0-9A-Z]{18}");
        ibanFormats.put("SC", "[A-Z]{4}\\d{2}\\d{2}\\d{16}[A-Z]{3}");
        ibanFormats.put("SE", "\\d{3}\\d{16}\\d{1}");
        ibanFormats.put("SI", "\\d{5}\\d{8}\\d{2}");
        ibanFormats.put("SK", "\\d{4}\\d{6}\\d{10}");
        ibanFormats.put("SM", "[A-Z]{1}\\d{5}\\d{5}[0-9A-Z]{12}");
        ibanFormats.put("ST", "\\d{8}\\d{11}\\d{2}");
        ibanFormats.put("SV", "[A-Z]{4}\\d{20}");
        ibanFormats.put("TL", "\\d{3}\\d{14}\\d{2}");
        ibanFormats.put("TN", "\\d{2}\\d{3}\\d{13}\\d{2}");
        ibanFormats.put("TR", "\\d{5}\\d{1}[0-9A-Z]{16}");
        ibanFormats.put("UA", "\\d{6}[0-9A-Z]{19}");
        ibanFormats.put("VA", "\\d{3}\\d{15}");
        ibanFormats.put("VG", "[A-Z]{4}\\d{16}");
        ibanFormats.put("XK", "\\d{4}\\d{10}\\d{2}");
        return ibanFormats;
    }
}
