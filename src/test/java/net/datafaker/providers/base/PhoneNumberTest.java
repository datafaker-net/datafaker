package net.datafaker.providers.base;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import net.datafaker.Faker;
import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class PhoneNumberTest extends BaseFakerTest<BaseFaker> {
    private static final Faker ESTONIAN = new Faker(new Locale("et", "EE"));
    private static final Faker MOLDOVAN = new Faker(new Locale("ro", "MD"));
    /**
     * Number of phone numbers to generate during a test
     */
    private static final int COUNT = 100;

    private final PhoneNumberUtil util = PhoneNumberUtil.getInstance();

    @Test
    void testCellPhone_enUS() {
        final BaseFaker f = new BaseFaker(Locale.US);
        String cellPhone = f.phoneNumber().cellPhone();
        assertThat(cellPhone).matches("\\(?\\d+\\)?([- .]\\d+){1,3}");
    }

    @RepeatedTest(COUNT)
    void testPhone_esMx() {
        final BaseFaker f = new BaseFaker(new Locale("es", "MX"));
        final PhoneNumber phoneNumber = f.phoneNumber();
        assertThat(phoneNumber.cellPhone()).matches("(044 )?\\(?\\d+\\)?([- .]\\d+){1,3}");
        assertThat(phoneNumber.phoneNumber()).matches("\\(?\\d+\\)?([- .]\\d+){1,3}");
    }

    @ParameterizedTest
    @MethodSource("canadianLocales")
    void testPhone_CA(Locale locale) {
        String areaCode = "263|354|382|403|587|780|825|236|250|368|428|604|672|778|204|431|506|"
            + "709|782|902|226|249|289|343|365|416|437|519|548|613|647|705|807|905|367|"
            + "418|438|450|468|474|514|579|581|584|683|742|753|819|873|306|639|867|879";
        Pattern canadianPhone = Pattern.compile("((\\+1)?(\\(?(%s)\\)?)|(%s))[- .]\\d{3}[- .]\\d{4}".formatted(areaCode, areaCode));
        PhoneNumber phoneNumber = new BaseFaker(locale).phoneNumber();
        for (int i = 0; i < COUNT; i++) {
            String phone = phoneNumber.cellPhone();
            assertThat(phone).matches(canadianPhone);
        }
    }

    private Stream<Arguments> canadianLocales() {
        return Stream.of(
            Arguments.of(Locale.CANADA),
            Arguments.of(new Locale("ca", "CA"))
        );
    }

    @ParameterizedTest
    @MethodSource("locales")
    void testAllPhoneNumberNational(Locale locale) throws NumberParseException {
        final BaseFaker faker = new BaseFaker(locale);
        final PhoneNumber phoneNumberProvider = faker.phoneNumber();
        for (int i = 0; i < COUNT; i++) {
            String phoneNumber = phoneNumberProvider.phoneNumber();
            Phonenumber.PhoneNumber proto = parse(phoneNumber, locale.getCountry());
            assertThat(util.isValidNumberForRegion(proto, locale.getCountry()))
                .as(() -> "Invalid phone %s for locale %s".formatted(phoneNumber, locale))
                .isTrue();
        }
    }

    @ParameterizedTest
    @MethodSource("locales")
    void testAllPhoneNumberInternational(Locale locale) throws NumberParseException {
        final BaseFaker faker = new BaseFaker(locale);
        final PhoneNumber phoneNumberProvider = faker.phoneNumber();
        for (int i = 0; i < COUNT; i++) {
            String phoneNumber = phoneNumberProvider.phoneNumberInternational();
            Phonenumber.PhoneNumber proto = parse(phoneNumber, locale.getCountry());
            assertThat(util.isValidNumberForRegion(proto, locale.getCountry()))
                .as(() -> "Invalid phone %s for locale %s".formatted(phoneNumber, locale))
                .isTrue();
        }
    }

    @ParameterizedTest
    @MethodSource("locales")
    void testAllPhoneNumberMobile(Locale locale) throws NumberParseException {
        final BaseFaker faker = new BaseFaker(locale);
        final PhoneNumber phoneNumberProvider = faker.phoneNumber();
        for (int i = 0; i < COUNT; i++) {
            String phoneNumber = phoneNumberProvider.cellPhone();
            Phonenumber.PhoneNumber proto = parse(phoneNumber, locale.getCountry());
            assertThat(util.isValidNumberForRegion(proto, locale.getCountry()))
                .as(() -> "Invalid phone %s for locale %s".formatted(phoneNumber, locale))
                .isTrue();
        }
    }

    @ParameterizedTest
    @MethodSource("locales")
    void testAllPhoneNumberMobileInternational(Locale locale) throws NumberParseException {
        final BaseFaker faker = new BaseFaker(locale);
        final PhoneNumber phoneNumberProvider = faker.phoneNumber();
        for (int i = 0; i < COUNT; i++) {
            String phoneNumber = phoneNumberProvider.cellPhoneInternational();
            Phonenumber.PhoneNumber proto = parse(phoneNumber, locale.getCountry());
            assertThat(util.isValidNumberForRegion(proto, locale.getCountry()))
                .as(() -> "Invalid phone %s for locale %s".formatted(phoneNumber, locale))
                .isTrue();
        }
    }

    private Phonenumber.PhoneNumber parse(String generatedNumber, String countryCode) throws NumberParseException {
        String normalizedNumber = "IT".equals(countryCode) || "HU".equals(countryCode) ? generatedNumber : generatedNumber.replaceFirst("^0(.+)", "$1");
        return util.parse(normalizedNumber, countryCode);
    }

    // `new Locale("en", "IND")` in `new Locale("en", "IND"), "IN")` is a Java's Locale

    // `"IN"` in `new Locale("en", "IND"), "IN")` is a PhoneNumberUtil's region
    private static Stream<Arguments> locales() {
        return Stream.of(
            locale("en", "US"),
            locale("en", "GB"),
            locale("en", "AU"),
            locale("en", "CA"),
            locale("en", "MS"),
            locale("en", "NG"),
            locale("en", "NZ"),
            locale("et", "EE"),
            locale("bg", "BG"),
            locale("by", "BY"),
            locale("ca", "CA"),
            locale("cs", "CZ"),
            locale("de", "DE"),
            locale("de", "AT"),
            locale("de", "CH"),
            locale("en", "IN"),
            locale("en", "NP"),
            locale("en", "PK"),
            locale("hu", "HU"),
            locale("fi", "FI"),
            locale("ko", "KR"),
            locale("ja", "JP"),
            locale("lv", "LV"),
            locale("mk", "MK"),
            locale("ca", "IT"),
            locale("nl", "NL"),
            locale("pl", "PL"),
            locale("pt", "PT"),
            locale("ro", "MD"),
            locale("sq", "AL"),
            locale("zh", "CN"),
            locale("zh", "TW"),
            locale("uk", "UA"),
            locale("tr", "TR"),
            locale("en", "SG"),
            locale("en", "PH"),
            locale("en", "UG"),
            locale("en", "ZA"),
            locale("sv", "SE"),
            locale("th", "TH"),
            locale("sk", "SK"),
            locale("ru", "RU"),
            locale("pt", "BR"),
            locale("es", "AR"),
            locale("es", "MX"),
            locale("es", "PY"),
            locale("es", "ES"),
            locale("fr", "CA"),
            locale("fr", "FR"),
            locale("he", "IL"),
            locale("hr", "HR"),
            locale("hy", "AM"),
            locale("id", "ID"),
            locale("nb", "NO"),
            locale("no", "NO"),
            locale("da", "DK"),
            locale("vi", "VI"),
            locale("fr", "CH")
        );
    }

    private static Arguments locale(String language, String country) {
        return Arguments.of(new Locale(language, country));
    }

    @Test
    void testCellPhone() {
        assertThat(faker.phoneNumber().cellPhone()).matches("\\(?\\d+\\)?([- .]\\d+){1,3}");
    }

    @Test
    void testPhoneNumber() {
        assertThat(faker.phoneNumber().phoneNumber()).matches("\\(?\\d+\\)?([- .]x?\\d+){1,5}");
    }

    @Test
    void testExtension() {
        assertThat(faker.phoneNumber().extension()).matches("\\d{4}");
    }

    @Test
    void testSubscriberNumber() {
        assertThat(faker.phoneNumber().subscriberNumber()).matches("\\d{4}");
    }

    @Test
    void testSubscriberNumberWithLength() {
        assertThat(faker.phoneNumber().subscriberNumber(10)).matches("\\d{10}");
    }

    @RepeatedTest(COUNT)
    void cellPhone_estonia() {
        String cellPhone = noSpaces(ESTONIAN.phoneNumber().cellPhone());
        assertThatPhone(cellPhone).matches("[358]\\d{6,7}");
    }

    @RepeatedTest(COUNT)
    void phoneNumberNational_estonia() {
        String cellPhone = noSpaces(ESTONIAN.phoneNumber().phoneNumberNational());
        assertThatPhone(cellPhone).matches("[34678]\\d{6,7}");
    }

    @RepeatedTest(COUNT)
    void phoneNumberInternational_estonia() {
        String cellPhone = noSpaces(ESTONIAN.phoneNumber().phoneNumberInternational());
        assertThatPhone(cellPhone).matches("\\+372[34678]\\d{6,7}");
    }

    @RepeatedTest(10)
    void cellPhone_moldova() {
        String phone = noSpaces(MOLDOVAN.phoneNumber().cellPhone());
        assertThatPhone(phone).matches("0[567]\\d{7}");
    }

    @RepeatedTest(10)
    void phoneNumber_moldova() {
        String phone = noSpaces(MOLDOVAN.phoneNumber().phoneNumber());
        assertThatPhone(phone).matches("0\\d{8}");
    }

    @RepeatedTest(10)
    void phoneNumberInternational_moldova() {
        String phone = noSpaces(MOLDOVAN.phoneNumber().phoneNumberInternational());
        assertThatPhone(phone).matches("\\+373\\d{8}");
    }

    private static AbstractStringAssert<?> assertThatPhone(String phoneNumber) {
        return assertThat(phoneNumber)
            .as(() -> "Phone: %s".formatted(phoneNumber));
    }

    private static String noSpaces(String phone) {
        return phone.replaceAll("\\s+", "");
    }
}
