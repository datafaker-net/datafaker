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

    public String iban() {
        List<String> countryCodes = new ArrayList<>(countryCodeToBasicBankAccountNumberPattern.keySet());
        String randomCountryCode = countryCodes.get(faker.random().nextInt(countryCodes.size()));
        return iban(randomCountryCode);
    }

    public String iban(String countryCode) {
        String basicBankAccountNumber = faker.regexify(countryCodeToBasicBankAccountNumberPattern.get(countryCode));
        String checkSum = calculateIbanChecksum(countryCode, basicBankAccountNumber);
        return countryCode + checkSum + basicBankAccountNumber;
    }

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
        // version 99
        Map<String, String> ibanFormats = new HashMap<>();
        ibanFormats.put("AD", "\\d{4}\\d{4}[0-9A-Z]{12}"); // 4!n4!n12!c
        ibanFormats.put("AE", "\\d{3}\\d{16}"); // 3!n16!n
        ibanFormats.put("AL", "\\d{8}[0-9A-Z]{16}"); // 8!n16!c
        ibanFormats.put("AT", "\\d{5}\\d{11}"); // 5!n11!n
        ibanFormats.put("AZ", "[A-Z]{4}[0-9A-Z]{20}"); // 4!a20!c
        ibanFormats.put("BA", "\\d{3}\\d{3}\\d{8}\\d{2}"); // 3!n3!n8!n2!n
        ibanFormats.put("BE", "\\d{3}\\d{7}\\d{2}"); // 3!n7!n2!n
        ibanFormats.put("BG", "[A-Z]{4}\\d{4}\\d{2}[0-9A-Z]{8}"); // 4!a4!n2!n8!c
        ibanFormats.put("BH", "[A-Z]{4}[0-9A-Z]{14}"); // 4!a14!c
        ibanFormats.put("BI", "\\d{5}\\d{5}\\d{11}\\d{2}"); //5!n5!n11!n2!n
        ibanFormats.put("BR", "\\d{8}\\d{5}\\d{10}[A-Z]{1}[0-9A-Z]{1}"); // 8!n5!n10!n1!a1!c
        ibanFormats.put("BY", "[0-9A-Z]{4}\\d{4}[0-9A-Z]{16}"); // 4!c4!n16!c
        ibanFormats.put("CH", "\\d{5}[0-9A-Z]{12}"); // 5!n12!c
        ibanFormats.put("CR", "\\d{4}\\d{14}"); // 4!n14!n
        ibanFormats.put("CY", "\\d{3}\\d{5}[0-9A-Z]{16}"); // 3!n5!n16!c
        ibanFormats.put("CZ", "\\d{4}\\d{6}\\d{10}"); // 4!n6!n10!n
        ibanFormats.put("DE", "\\d{8}\\d{10}"); // 8!n10!n
        ibanFormats.put("DJ", "\\d{5}\\d{5}\\d{11}\\d{2}"); // 5!n5!n11!n2!n
        ibanFormats.put("DK", "\\d{4}\\d{9}\\d{1}"); // 4!n9!n1!n
        ibanFormats.put("DO", "[0-9A-Z]{4}\\d{20}"); // 4!c20!n
        ibanFormats.put("EE", "\\d{2}\\d{14}"); // 2!n14!n
        ibanFormats.put("EG", "\\d{4}\\d{4}\\d{17}"); // 4!n4!n17!n
        ibanFormats.put("ES", "\\d{4}\\d{4}\\d{1}\\d{1}\\d{10}"); // 4!n4!n1!n1!n10!n
        ibanFormats.put("FI", "\\d{3}\\d{11}"); // 3!n11!n
        ibanFormats.put("FK", "[A-Z]{2}\\d{12}"); // 2!a12!n
        ibanFormats.put("FO", "\\d{4}\\d{9}\\d{1}"); // 4!n9!n1!n
        ibanFormats.put("FR", "\\d{5}\\d{5}[0-9A-Z]{11}\\d{2}"); // 5!n5!n11!c2!n
        ibanFormats.put("GB", "[A-Z]{4}\\d{6}\\d{8}"); // 4!a6!n8!n
        ibanFormats.put("GE", "[A-Z]{2}\\d{16}"); // 2!a16!n
        ibanFormats.put("GI", "[A-Z]{4}[0-9A-Z]{15}"); // 4!a15!c
        ibanFormats.put("GL", "\\d{4}\\d{9}\\d{1}"); // 4!n9!n1!n
        ibanFormats.put("GR", "\\d{3}\\d{4}[0-9A-Z]{16}"); // 3!n4!n16!c
        ibanFormats.put("GT", "[0-9A-Z]{4}[0-9A-Z]{20}"); // 4!c20!c
        ibanFormats.put("HN", "[A-Z]{4}\\d{20}"); // 4!a20!n
        ibanFormats.put("HR", "\\d{7}\\d{10}"); // 7!n10!n
        ibanFormats.put("HU", "\\d{3}\\d{4}\\d{1}\\d{15}\\d{1}"); // 3!n4!n1!n15!n1!n
        ibanFormats.put("IE", "[A-Z]{4}\\d{6}\\d{8}"); // 4!a6!n8!n
        ibanFormats.put("IL", "\\d{3}\\d{3}\\d{13}"); // 3!n3!n13!n
        ibanFormats.put("IQ", "[A-Z]{4}\\d{3}\\d{12}"); // 4!a3!n12!n
        ibanFormats.put("IS", "\\d{4}\\d{2}\\d{6}\\d{10}"); // 4!n2!n6!n10!n
        ibanFormats.put("IT", "[A-Z]{1}\\d{5}\\d{5}[0-9A-Z]{12}"); // 1!a5!n5!n12!c
        ibanFormats.put("JO", "[A-Z]{4}\\d{4}[0-9A-Z]{18}"); // 4!a4!n18!c
        ibanFormats.put("KW", "[A-Z]{4}[0-9A-Z]{22}"); // 4!a22!c
        ibanFormats.put("KZ", "\\d{3}[0-9A-Z]{13}"); // 3!n13!c
        ibanFormats.put("LB", "\\d{4}[0-9A-Z]{20}"); // 4!n20!c
        ibanFormats.put("LC", "[A-Z]{4}[0-9A-Z]{24}"); // 4!a24!c
        ibanFormats.put("LI", "\\d{5}[0-9A-Z]{12}"); // 5!n12!c
        ibanFormats.put("LT", "\\d{5}\\d{11}"); // 5!n11!n
        ibanFormats.put("LU", "\\d{3}[0-9A-Z]{13}"); // 3!n13!c
        ibanFormats.put("LV", "[A-Z]{4}[0-9A-Z]{13}"); // 4!a13!c
        ibanFormats.put("LY", "\\d{3}\\d{3}\\d{15}"); // 3!n3!n15!n
        ibanFormats.put("MC", "\\d{5}\\d{5}[0-9A-Z]{11}\\d{2}"); // 5!n5!n11!c2!n
        ibanFormats.put("MD", "[0-9A-Z]{2}[0-9A-Z]{18}"); // 2!c18!c
        ibanFormats.put("ME", "\\d{3}\\d{13}\\d{2}"); // 3!n13!n2!n
        ibanFormats.put("MK", "\\d{3}[0-9A-Z]{10}\\d{2}"); // 3!n10!c2!n
        ibanFormats.put("MN", "\\d{4}\\d{12}"); // 4!n12!n
        ibanFormats.put("MR", "\\d{5}\\d{5}\\d{11}\\d{2}"); // 5!n5!n11!n2!n
        ibanFormats.put("MT", "[A-Z]{4}\\d{5}[0-9A-Z]{18}"); // 4!a5!n18!c
        ibanFormats.put("MU", "[A-Z]{4}\\d{2}\\d{2}\\d{12}\\d{3}[A-Z]{3}"); // 4!a2!n2!n12!n3!n3!a
        ibanFormats.put("NI", "[A-Z]{4}\\d{20}"); // 4!a20!n
        ibanFormats.put("NL", "[A-Z]{4}\\d{10}"); // 4!a10!n
        ibanFormats.put("NO", "\\d{4}\\d{6}\\d{1}"); // 4!n6!n1!n
        ibanFormats.put("OM", "\\d{3}[0-9A-Z]{16}"); // 3!n16!c
        ibanFormats.put("PK", "[A-Z]{4}[0-9A-Z]{16}"); // 4!a16!c
        ibanFormats.put("PL", "\\d{8}\\d{16}"); // 8!n16!n
        ibanFormats.put("PS", "[A-Z]{4}[0-9A-Z]{21}"); // 4!a21!c
        ibanFormats.put("PT", "\\d{4}\\d{4}\\d{11}\\d{2}"); // 4!n4!n11!n2!n
        ibanFormats.put("QA", "[A-Z]{4}[0-9A-Z]{21}"); // 4!a21!c
        ibanFormats.put("RO", "[A-Z]{4}[0-9A-Z]{16}"); // 4!a16!c
        ibanFormats.put("RS", "\\d{3}\\d{13}\\d{2}"); // 3!n13!n2!n
        ibanFormats.put("RU", "\\d{9}\\d{5}[0-9A-Z]{15}"); // 9!n5!n15!c
        ibanFormats.put("SA", "\\d{2}[0-9A-Z]{18}"); // 2!n18!c
        ibanFormats.put("SC", "[A-Z]{4}\\d{2}\\d{2}\\d{16}[A-Z]{3}"); // 4!a2!n2!n16!n3!a
        ibanFormats.put("SD", "\\d{2}\\d{12}"); // 2!n12!n
        ibanFormats.put("SE", "\\d{3}\\d{16}\\d{1}"); // 3!n16!n1!n
        ibanFormats.put("SI", "\\d{5}\\d{8}\\d{2}"); // 5!n8!n2!n
        ibanFormats.put("SK", "\\d{4}\\d{6}\\d{10}"); // 4!n6!n10!n
        ibanFormats.put("SM", "[A-Z]{1}\\d{5}\\d{5}[0-9A-Z]{12}"); // 1!a5!n5!n12!c
        ibanFormats.put("SO", "\\d{2}\\d{12}"); // 2!n12!n
        ibanFormats.put("ST", "\\d{8}\\d{11}\\d{2}"); // 8!n11!n2!n
        ibanFormats.put("SV", "[A-Z]{4}\\d{20}"); // 4!a20!n
        ibanFormats.put("TL", "\\d{3}\\d{14}\\d{2}"); // 3!n14!n2!n
        ibanFormats.put("TN", "\\d{2}\\d{3}\\d{13}\\d{2}"); // 2!n3!n13!n2!n
        ibanFormats.put("TR", "\\d{5}\\d{1}[0-9A-Z]{16}"); // 5!n1!n16!c
        ibanFormats.put("UA", "\\d{6}[0-9A-Z]{19}"); // 6!n19!c
        ibanFormats.put("VA", "\\d{3}\\d{15}"); // 3!n15!n
        ibanFormats.put("VG", "[A-Z]{4}\\d{16}"); // 4!a16!n
        ibanFormats.put("XK", "\\d{4}\\d{10}\\d{2}"); // 4!n10!n2!n
        ibanFormats.put("YE", "[A-Z]{4}\\d{4}[0-9A-Z]{18}"); // 4!a4!n18!c
        return ibanFormats;
    }
}
