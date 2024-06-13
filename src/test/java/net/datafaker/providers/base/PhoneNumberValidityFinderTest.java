package net.datafaker.providers.base;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.stream.Stream;

import static java.util.Locale.ROOT;
import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumberValidityFinderTest extends BaseFakerTest<BaseFaker> {
    private static final int COUNT = 100;
    private final PhoneNumberUtil util = PhoneNumberUtil.getInstance();

    @RepeatedTest(COUNT)
    void testAllCellPhoneForLocale() throws NumberParseException {
        String language = "en";
        String region = "GB";
        BaseFaker localFaker = new BaseFaker(new Locale(language, region));

        String generatedNumber = localFaker.phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber parsedNumber = util.parse(generatedNumber, region);

        assertThat(util.isValidNumber(parsedNumber))
            .as(() -> "Generated phone number %s for region %s".formatted(generatedNumber, region))
            .isTrue();
    }

    @Test
    void testValidNumber() throws NumberParseException {
        String phoneNumber = "0140 123456";
        String region = "SE";

        Phonenumber.PhoneNumber parsedNumber = util.parse(phoneNumber, region);
        assertThat(util.isValidNumber(parsedNumber)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("allSupportedLocales")
    void testAllPhoneNumbers(Locale supportedLocale) throws NumberParseException {
        BaseFaker f = new BaseFaker(supportedLocale);
        PhoneNumber phoneNumberGenerator = f.phoneNumber();
        for (int i = 0; i < COUNT; i++) {
            String generatedNumber = phoneNumberGenerator.phoneNumber();
            Phonenumber.PhoneNumber parsedNumber = parse(generatedNumber, phoneNumberGenerator.countryCodeIso2());

            assertThat(util.isValidNumber(parsedNumber))
                .as(() -> "Generated phone number %s for locale %s (country: %s)".formatted(generatedNumber, supportedLocale, phoneNumberGenerator.countryCodeIso2()))
                .isTrue();
        }
    }

    private Phonenumber.PhoneNumber parse(String generatedNumber, String countryCode) throws NumberParseException {
        String normalizedNumber = "IT".equals(countryCode) || "HU".equals(countryCode) ? generatedNumber : generatedNumber.replaceFirst("^0(.+)", "$1");
        return util.parse(normalizedNumber, countryCode);
    }

    public Stream<Arguments> allSupportedLocales() {
        return faker.locality().allSupportedLocales().stream()
            .map(rawLocale -> Arguments.of(createLocale(rawLocale)));
    }

    private static Locale createLocale(String locale) {
        if (locale.startsWith("_")) {
            return new Locale("en", locale.substring(1).toUpperCase(ROOT));
        }
        if (locale.contains("-")) {
            String[] parts = locale.split("-");
            return new Locale(parts[0], parts[1]);
        }
        return new Locale(locale, "");
    }
}
