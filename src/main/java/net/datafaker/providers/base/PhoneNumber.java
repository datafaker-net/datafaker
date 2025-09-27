package net.datafaker.providers.base;

import net.datafaker.annotations.InternalApi;
import net.datafaker.annotations.Deterministic;

import java.util.Locale;

import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL;
import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL;
import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.FIXED_LINE;
import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.MOBILE;
import static java.util.Locale.ROOT;

/**
 * @since 0.8.0
 */
public class PhoneNumber extends AbstractProvider<BaseProviders> {
    private final String countryCodeIso2;
    private final PhoneNumberGenerator generator;

    protected PhoneNumber(BaseProviders faker) {
        super(faker);
        this.countryCodeIso2 = countryCodeIso2(faker.getContext().getLocale());
        this.generator = new PhoneNumberGenerator(faker.fakeValuesService(), faker.getContext());
    }

    @Deterministic
    @InternalApi
    String countryCodeIso2() {
        return countryCodeIso2;
    }

    public String cellPhone() {
        return generator.randomPhoneNumber(countryCodeIso2, MOBILE, NATIONAL);
    }

    public String cellPhoneInternational() {
        return generator.randomPhoneNumber(countryCodeIso2, MOBILE, INTERNATIONAL);
    }

    /**
     * Generates locale specific phone number in national format.
     *
     * @return phone number
     */
    public String phoneNumber() {
        return phoneNumberNational();
    }

    public String phoneNumberInternational() {
        return generator.randomPhoneNumber(countryCodeIso2, FIXED_LINE, INTERNATIONAL);
    }

    public String phoneNumberNational() {
        return generator.randomPhoneNumber(countryCodeIso2, FIXED_LINE, NATIONAL);
    }

    public String extension() {
        return subscriberNumber();
    }

    public String subscriberNumber(int length) {
        return faker.numerify("#".repeat(Math.max(0, length)));
    }

    public String subscriberNumber() {
        return subscriberNumber(4);
    }

    private static String countryCodeIso2(Locale locale) {
        String country = locale.getCountry();
        return switch (country) {
            case "" -> detectCountryByLanguage(locale.getLanguage());
            case "CAT" -> "ES";
            case "NEP" -> "NP";
            case "PAK" -> "PK";
            case "BORK" -> "US"; // what the hell is BORK?
            default -> country;
        };
    }

    /**
     * A hack to detect country when only a language is given.
     * <p>
     *     It's not correct because most languages are used in multiple countries.
     *     If users need to generate random phone number, they should create locale with country,
     *     e.g. {@code new Locale("ta_IN")}, and not just {@code new Locale("ta")}.
     * </p>
     * <p>
     *     We keep this mapping here just for backward compatibility.
     * </p>
     */
    private static String detectCountryByLanguage(String language) {
        return switch (language) {
            case "en" -> "US"; // it has been used by default for English
            case "test" -> "US"; // What the hell is "test" language?
            case "hy" -> "AM"; // Armenia
            case "uk" -> "UA"; // Ukraine
            case "ja" -> "JP"; // Japan
            case "fa" -> "FR"; // France
            case "ka" -> "GE"; // Georgia
            case "sq" -> "AL"; // Albania
            case "cs" -> "CZ"; // Czech Republic
            case "be" -> "BY"; // Belarus
            case "ko" -> "KR"; // Korea
            case "he" -> "IL"; // Israel
            case "ta" -> "IN"; // Tamil language -> India (though, Tamil is used in multiple countries)
            default -> language.toUpperCase(ROOT);
        };
    }
}
