package net.datafaker.providers.base;

import net.datafaker.annotations.Deterministic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        String countryCode = faker.options().nextElement(countryCodes);
        return iban(countryCode);
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
        // source: - https://www.swift.com/standards/data-standards/iban, version 101
        //         - de.speedbanking:iban-commons de.speedbanking.iban.tool.DatafakerFinanceIbanGenerator
        Map<String, String> ibanFormats = new LinkedHashMap<>();
        ibanFormats.put("AD", "[0-9]{8}[0-9A-Z]{12}");            // 🇦🇩 Andorra                  : 4!n4!n12!c
        ibanFormats.put("AE", "[0-9]{19}");                       // 🇦🇪 United Arab Emirates     : 3!n16!n
        ibanFormats.put("AL", "[0-9]{8}[0-9A-Z]{16}");            // 🇦🇱 Albania                  : 8!n16!c
        ibanFormats.put("AO", "[0-9]{21}");                       // 🇦🇴 Angola                   : 4!n4!n11!n2!n
        ibanFormats.put("AT", "[0-9]{16}");                       // 🇦🇹 Austria                  : 5!n11!n
        ibanFormats.put("AZ", "[A-Z]{4}[0-9A-Z]{20}");            // 🇦🇿 Azerbaijan               : 4!a20!c
        ibanFormats.put("BA", "[0-9]{16}");                       // 🇧🇦 Bosnia and Herzegovina   : 3!n3!n8!n2!n
        ibanFormats.put("BE", "[0-9]{12}");                       // 🇧🇪 Belgium                  : 3!n7!n2!n
        ibanFormats.put("BG", "[A-Z]{4}[0-9]{6}[0-9A-Z]{8}");     // 🇧🇬 Bulgaria                 : 4!a4!n2!n8!c
        ibanFormats.put("BH", "[A-Z]{4}[0-9A-Z]{14}");            // 🇧🇭 Bahrain                  : 4!a14!c
        ibanFormats.put("BI", "[0-9]{23}");                       // 🇧🇮 Burundi                  : 5!n5!n11!n2!n
        ibanFormats.put("BR", "[0-9]{23}[A-Z][0-9A-Z]");          // 🇧🇷 Brazil                   : 8!n5!n10!n1!a1!c
        ibanFormats.put("BY", "[0-9A-Z]{4}[0-9]{4}[0-9A-Z]{16}"); // 🇧🇾 Belarus                  : 4!c4!n16!c
        ibanFormats.put("CH", "[0-9]{5}[0-9A-Z]{12}");            // 🇨🇭 Switzerland              : 5!n12!c
        ibanFormats.put("CR", "[0-9]{18}");                       // 🇨🇷 Costa Rica               : 4!n14!n
        ibanFormats.put("CV", "[0-9]{8}[0-9A-Z]{13}");            // 🇨🇻 Cape Verde               : 4!n4!n13!c
        ibanFormats.put("CY", "[0-9]{8}[0-9A-Z]{16}");            // 🇨🇾 Cyprus                   : 3!n5!n16!c
        ibanFormats.put("CZ", "[0-9]{20}");                       // 🇨🇿 Czechia                  : 4!n16!n
        ibanFormats.put("DE", "[0-9]{18}");                       // 🇩🇪 Germany                  : 8!n10!n
        ibanFormats.put("DJ", "[0-9]{23}");                       // 🇩🇯 Djibouti                 : 5!n5!n11!n2!n
        ibanFormats.put("DK", "[0-9]{14}");                       // 🇩🇰 Denmark                  : 4!n9!n1!n
        ibanFormats.put("DO", "[0-9A-Z]{4}[0-9]{20}");            // 🇩🇴 Dominican Republic       : 4!c20!n
        ibanFormats.put("EE", "[0-9]{16}");                       // 🇪🇪 Estonia                  : 2!n14!n
        ibanFormats.put("EG", "[0-9]{25}");                       // 🇪🇬 Egypt                    : 4!n4!n17!n
        ibanFormats.put("ES", "[0-9]{20}");                       // 🇪🇸 Spain                    : 4!n4!n1!n1!n10!n
        ibanFormats.put("FI", "[0-9]{14}");                       // 🇫🇮 Finland                  : 3!n11!n
        ibanFormats.put("FK", "[A-Z]{2}[0-9]{12}");               // 🇫🇰 Falkland Islands         : 2!a12!n
        ibanFormats.put("FO", "[0-9]{14}");                       // 🇫🇴 Faroe Islands            : 4!n9!n1!n
        ibanFormats.put("FR", "[0-9]{10}[0-9A-Z]{11}[0-9]{2}");   // 🇫🇷 France                   : 5!n5!n11!c2!n
        ibanFormats.put("GA", "[0-9]{10}[0-9A-Z]{13}");           // 🇬🇦 Gabon                    : 5!n5!n13!c
        ibanFormats.put("GB", "[A-Z]{4}[0-9]{14}");               // 🇬🇧 United Kingdom           : 4!a6!n8!n
        ibanFormats.put("GE", "[A-Z]{2}[0-9]{16}");               // 🇬🇪 Georgia                  : 2!a16!n
        ibanFormats.put("GI", "[A-Z]{4}[0-9A-Z]{15}");            // 🇬🇮 Gibraltar                : 4!a15!c
        ibanFormats.put("GL", "[0-9]{14}");                       // 🇬🇱 Greenland                : 4!n9!n1!n
        ibanFormats.put("GR", "[0-9]{7}[0-9A-Z]{16}");            // 🇬🇷 Greece                   : 3!n4!n16!c
        ibanFormats.put("GT", "[0-9A-Z]{24}");                    // 🇬🇹 Guatemala                : 4!c20!c
        ibanFormats.put("HN", "[A-Z]{4}[0-9]{20}");               // 🇭🇳 Honduras                 : 4!a20!n
        ibanFormats.put("HR", "[0-9]{17}");                       // 🇭🇷 Croatia                  : 7!n10!n
        ibanFormats.put("HU", "[0-9]{24}");                       // 🇭🇺 Hungary                  : 3!n4!n1!n15!n1!n
        ibanFormats.put("IE", "[A-Z]{4}[0-9]{14}");               // 🇮🇪 Ireland                  : 4!a6!n8!n
        ibanFormats.put("IL", "[0-9]{19}");                       // 🇮🇱 Israel                   : 3!n3!n13!n
        ibanFormats.put("IQ", "[A-Z]{4}[0-9]{15}");               // 🇮🇶 Iraq                     : 4!a3!n12!n
        ibanFormats.put("IR", "[0-9]{22}");                       // 🇮🇷 Islamic Republic of Iran : 3!n19!n
        ibanFormats.put("IS", "[0-9]{22}");                       // 🇮🇸 Iceland                  : 4!n2!n6!n10!n
        ibanFormats.put("IT", "[A-Z][0-9]{10}[0-9A-Z]{12}");      // 🇮🇹 Italy                    : 1!a5!n5!n12!c
        ibanFormats.put("JO", "[A-Z]{4}[0-9]{4}[0-9A-Z]{18}");    // 🇯🇴 Jordan                   : 4!a4!n18!c
        ibanFormats.put("KW", "[A-Z]{4}[0-9A-Z]{22}");            // 🇰🇼 Kuwait                   : 4!a22!c
        ibanFormats.put("KZ", "[0-9]{3}[0-9A-Z]{13}");            // 🇰🇿 Kazakhstan               : 3!n13!c
        ibanFormats.put("LB", "[0-9]{4}[0-9A-Z]{20}");            // 🇱🇧 Lebanon                  : 4!n20!c
        ibanFormats.put("LC", "[A-Z]{4}[0-9A-Z]{24}");            // 🇱🇨 Saint Lucia              : 4!a24!c
        ibanFormats.put("LI", "[0-9]{5}[0-9A-Z]{12}");            // 🇱🇮 Liechtenstein            : 5!n12!c
        ibanFormats.put("LT", "[0-9]{16}");                       // 🇱🇹 Lithuania                : 5!n11!n
        ibanFormats.put("LU", "[0-9]{3}[0-9A-Z]{13}");            // 🇱🇺 Luxembourg               : 3!n13!c
        ibanFormats.put("LV", "[A-Z]{4}[0-9A-Z]{13}");            // 🇱🇻 Latvia                   : 4!a13!c
        ibanFormats.put("LY", "[0-9]{21}");                       // 🇱🇾 Libya                    : 3!n3!n15!n
        ibanFormats.put("MA", "[0-9]{24}");                       // 🇲🇦 Morocco                  : 3!n5!n16!n
        ibanFormats.put("MC", "[0-9]{10}[0-9A-Z]{11}[0-9]{2}");   // 🇲🇨 Monaco                   : 5!n5!n11!c2!n
        ibanFormats.put("MD", "[0-9A-Z]{20}");                    // 🇲🇩 Moldova                  : 2!c18!c
        ibanFormats.put("ME", "[0-9]{18}");                       // 🇲🇪 Montenegro               : 3!n13!n2!n
        ibanFormats.put("MK", "[0-9]{3}[0-9A-Z]{10}[0-9]{2}");    // 🇲🇰 North Macedonia          : 3!n10!c2!n
        ibanFormats.put("MN", "[0-9]{16}");                       // 🇲🇳 Mongolia                 : 4!n12!n
        ibanFormats.put("MR", "[0-9]{23}");                       // 🇲🇷 Mauritania               : 5!n5!n11!n2!n
        ibanFormats.put("MT", "[A-Z]{4}[0-9]{5}[0-9A-Z]{18}");    // 🇲🇹 Malta                    : 4!a5!n18!c
        ibanFormats.put("MU", "[A-Z]{4}[0-9]{19}[A-Z]{3}");       // 🇲🇺 Mauritius                : 4!a2!n2!n12!n3!n3!a
        ibanFormats.put("MZ", "[0-9]{21}");                       // 🇲🇿 Mozambique               : 4!n4!n11!n2!n
        ibanFormats.put("NI", "[A-Z]{4}[0-9]{20}");               // 🇳🇮 Nicaragua                : 4!a20!n
        ibanFormats.put("NL", "[A-Z]{4}[0-9]{10}");               // 🇳🇱 Netherlands              : 4!a10!n
        ibanFormats.put("NO", "[0-9]{11}");                       // 🇳🇴 Norway                   : 4!n6!n1!n
        ibanFormats.put("OM", "[0-9]{3}[0-9A-Z]{16}");            // 🇴🇲 Oman                     : 3!n16!c
        ibanFormats.put("PK", "[A-Z]{4}[0-9A-Z]{16}");            // 🇵🇰 Pakistan                 : 4!a16!c
        ibanFormats.put("PL", "[0-9]{24}");                       // 🇵🇱 Poland                   : 8!n16!n
        ibanFormats.put("PS", "[A-Z]{4}[0-9A-Z]{21}");            // 🇵🇸 Palestine                : 4!a21!c
        ibanFormats.put("PT", "[0-9]{21}");                       // 🇵🇹 Portugal                 : 4!n4!n11!n2!n
        ibanFormats.put("QA", "[A-Z]{4}[0-9A-Z]{21}");            // 🇶🇦 Qatar                    : 4!a21!c
        ibanFormats.put("RO", "[A-Z]{4}[0-9A-Z]{16}");            // 🇷🇴 Romania                  : 4!a16!c
        ibanFormats.put("RS", "[0-9]{18}");                       // 🇷🇸 Serbia                   : 3!n13!n2!n
        ibanFormats.put("RU", "[0-9]{14}[0-9A-Z]{15}");           // 🇷🇺 Russia                   : 9!n5!n15!c
        ibanFormats.put("SA", "[0-9]{2}[0-9A-Z]{18}");            // 🇸🇦 Saudi Arabia             : 2!n18!c
        ibanFormats.put("SC", "[A-Z]{4}[0-9]{20}[A-Z]{3}");       // 🇸🇨 Seychelles               : 4!a2!n2!n16!n3!a
        ibanFormats.put("SD", "[0-9]{14}");                       // 🇸🇩 Sudan                    : 2!n12!n
        ibanFormats.put("SE", "[0-9]{20}");                       // 🇸🇪 Sweden                   : 3!n16!n1!n
        ibanFormats.put("SI", "[0-9]{15}");                       // 🇸🇮 Slovenia                 : 2!n3!n8!n2!n
        ibanFormats.put("SK", "[0-9]{20}");                       // 🇸🇰 Slovakia                 : 4!n6!n10!n
        ibanFormats.put("SM", "[A-Z][0-9]{10}[0-9A-Z]{12}");      // 🇸🇲 San Marino               : 1!a5!n5!n12!c
        ibanFormats.put("SO", "[0-9]{19}");                       // 🇸🇴 Somalia                  : 4!n3!n12!n
        ibanFormats.put("ST", "[0-9]{21}");                       // 🇸🇹 Sao Tome and Principe    : 4!n4!n11!n2!n
        ibanFormats.put("SV", "[A-Z]{4}[0-9]{20}");               // 🇸🇻 El Salvador              : 4!a20!n
        ibanFormats.put("TL", "[0-9]{19}");                       // 🇹🇱 Timor-Leste              : 3!n14!n2!n
        ibanFormats.put("TN", "[0-9]{20}");                       // 🇹🇳 Tunisia                  : 2!n3!n13!n2!n
        ibanFormats.put("TR", "[0-9]{6}[0-9A-Z]{16}");            // 🇹🇷 Turkey                   : 5!n1!n16!c
        ibanFormats.put("UA", "[0-9]{6}[0-9A-Z]{19}");            // 🇺🇦 Ukraine                  : 6!n19!c
        ibanFormats.put("VA", "[0-9]{18}");                       // 🇻🇦 Vatican City State       : 3!n15!n
        ibanFormats.put("VG", "[A-Z]{4}[0-9]{16}");               // 🇻🇬 Virgin Islands           : 4!a16!n
        ibanFormats.put("XK", "[0-9]{16}");                       // 🇽🇰 Kosovo                   : 4!n10!n2!n
        ibanFormats.put("YE", "[A-Z]{4}[0-9]{4}[0-9A-Z]{18}");    // 🇾🇪 Yemen                    : 4!a4!n18!c
        ibanFormats.put("BF", "[0-9A-Z]{5}[0-9]{18}");            // 🇧🇫 Burkina Faso             : 5!c5!n11!n2!n
        ibanFormats.put("BJ", "[0-9A-Z]{5}[0-9]{19}");            // 🇧🇯 Benin                    : 5!c5!n12!n2!n
        ibanFormats.put("CF", "[0-9]{23}");                       // 🇨🇫 Central African Republic : 5!n5!n11!n2!n
        ibanFormats.put("CM", "[0-9]{23}");                       // 🇨🇲 Cameroon                 : 5!n5!n11!n2!n
        ibanFormats.put("DZ", "[0-9]{20}");                       // 🇩🇿 Algeria                  : 3!n5!n10!n2!n
        ibanFormats.put("GQ", "[0-9]{23}");                       // 🇬🇶 Equatorial Guinea        : 5!n5!n11!n2!n
        ibanFormats.put("KM", "[0-9]{23}");                       // 🇰🇲 Comoros                  : 5!n5!n11!n2!n
        ibanFormats.put("SN", "[0-9A-Z]{5}[0-9]{19}");            // 🇸🇳 Senegal                  : 5!c5!n12!n2!n
        ibanFormats.put("TG", "[0-9A-Z]{5}[0-9]{19}");            // 🇹🇬 Togo                     : 5!c5!n12!n2!n
        return ibanFormats;
    }
}
