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
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.logging.Level.FINE;
import static org.assertj.core.api.Assertions.assertThat;


class PhoneNumberTest extends BaseFakerTest<BaseFaker> {
    private static final Logger log = Logger.getLogger(PhoneNumberTest.class.getName());
    private static final Faker ESTONIAN = new Faker(new Locale("et", "EE"));
    private static final Faker MOLDOVAN = new Faker(new Locale("ro", "MD"));
    /**
     * Number of phone numbers to generate during a test
     */
    private static final int COUNT = 100;
    /**
     * For most countries, number of invalid generated phone numbers is < 77% - it needs to be improved.
     */
    private static final int PARTIALLY_CORRECT = (int) (COUNT * 0.77);
    /**
     * For few countries, most of generated phone numbers are invalid - it definitely needs to be improved.
     */
    private static final int FIXME = COUNT;
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

    @Test
    void testPhone_CA() {
        final Locale[] locales = {Locale.CANADA, new Locale("ca")};
        for (Locale locale : locales) {
            final BaseFaker f = new BaseFaker(locale);
            final String canadianAreaCode = "403|587|780|825|236|250|604|672|778|204|431|506|"
                + "709|782|902|226|249|289|343|365|416|437|519|548|613|647|705|807|905|367|"
                + "418|438|450|514|579|581|819|873|306|639|867";
            final PhoneNumber phoneNumber = f.phoneNumber();
            for (int i = 0; i < COUNT; i++) {
                String phone = phoneNumber.cellPhone();
                assertThat(phone).matches(
                    "((1-)?(\\(?(%s)\\)?)|(%s))[- .]\\d{3}[- .]\\d{4}".formatted(
                        canadianAreaCode, canadianAreaCode));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("generateLanguageAndRegionOfLocales")
    void testAllPhoneNumberNational(Locale locale, String phoneNumberRegion, int allowedErrorsCount) {
        int errorCount = 0;
        final BaseFaker faker = new BaseFaker(locale);
        final PhoneNumber phoneNumberProvider = faker.phoneNumber();
        for (int i = 0; i < COUNT; i++) {
            String phoneNumber = phoneNumberProvider.phoneNumber();
            try {
                Phonenumber.PhoneNumber proto = util.parse(phoneNumber, phoneNumberRegion);
                if (!util.isValidNumberForRegion(proto, phoneNumberRegion)) {
                    if (log.isLoggable(FINE)) {
                        log.fine("Invalid phone: %s".formatted(phoneNumber));
                    }
                    errorCount++;
                }
            } catch (NumberParseException e) {
                if (log.isLoggable(FINE)) {
                    log.fine("Invalid phone: %s (caused by: %s)".formatted(phoneNumber, e));
                }
                errorCount++;
            }
        }
        assertThat(errorCount).isLessThanOrEqualTo(allowedErrorsCount);
    }

    @ParameterizedTest
    @MethodSource("generateLanguageAndRegionOfLocales")
    void testAllPhoneNumberInternational(Locale locale, String phoneNumberRegion, int allowedErrorsCount) throws NumberParseException {
        int errorCount = 0;
        final BaseFaker faker = new BaseFaker(locale);
        final PhoneNumber phoneNumberProvider = faker.phoneNumber();
        for (int i = 0; i < COUNT; i++) {
            String phoneNumber = phoneNumberProvider.phoneNumberInternational();
            Phonenumber.PhoneNumber proto = util.parse(phoneNumber, phoneNumberRegion);
            if (!util.isValidNumberForRegion(proto, phoneNumberRegion)) {
                if (log.isLoggable(FINE)) {
                    log.fine("Invalid phone: %s".formatted(phoneNumber));
                }
                errorCount++;
            }
        }
        assertThat(errorCount).isLessThanOrEqualTo(allowedErrorsCount);
    }

    @ParameterizedTest
    @MethodSource("generateLanguageAndRegionOfLocales")
    void testAllPhoneNumberMobile(Locale locale, String phoneNumberRegion, int allowedErrorsCount) throws NumberParseException {
        int errorCount = 0;
        final BaseFaker faker = new BaseFaker(locale);
        final PhoneNumber phoneNumberProvider = faker.phoneNumber();
        for (int i = 0; i < COUNT; i++) {
            String phoneNumber = phoneNumberProvider.cellPhone();
            Phonenumber.PhoneNumber proto = util.parse(phoneNumber, phoneNumberRegion);
            if (!util.isValidNumberForRegion(proto, phoneNumberRegion)) {
                if (log.isLoggable(FINE)) {
                    log.fine("Invalid phone: %s".formatted(phoneNumber));
                }
                errorCount++;
            }
        }
        assertThat(errorCount).isLessThanOrEqualTo(allowedErrorsCount);
    }

    private static Arguments args(Locale locale, String phoneNumberRegion) {
        return args(locale, phoneNumberRegion, PARTIALLY_CORRECT);
    }

    private static Arguments args(Locale locale, String phoneNumberRegion, int allowedErrorsCount) {
        return Arguments.of(locale, phoneNumberRegion, allowedErrorsCount);
    }

    // `new Locale("en", "IND")` in `new Locale("en", "IND"), "IN")` is a Java's Locale
    // `"IN"` in `new Locale("en", "IND"), "IN")` is a PhoneNumberUtil's region
    private static Stream<Arguments> generateLanguageAndRegionOfLocales() {
        return Stream.of(
            args(new Locale("en", "US"), "US"),
            args(new Locale("en", "GB"), "GB"),
            args(new Locale("en", "AU"), "AU"),
            args(new Locale("en", "CA"), "CA"),
            args(new Locale("en", "MS"), "MS", FIXME),
            args(new Locale("en", "NG"), "NG", FIXME),
            args(new Locale("en", "NZ"), "NZ"),
            args(new Locale("et", "EE"), "EE", 0),
            args(new Locale("bg", "BG"), "BG", 0),
            args(new Locale("by", "BY"), "BY", FIXME),
            args(new Locale("ca", "CA"), "CA"),
            args(new Locale("cs", "CZ"), "CZ"),
            args(new Locale("de"), "DE"),
            args(new Locale("de", "AT"), "AT"),
            args(new Locale("de", "CH"), "CH", FIXME),
            args(new Locale("en", "IND"), "IN"),
            args(new Locale("en", "NEP"), "NP", FIXME),
            args(new Locale("en", "PAK"), "PK", FIXME),
            args(new Locale("hu", "HU"), "HU"),
            args(new Locale("fi", "FI"), "FI"),
            args(new Locale("ko", "KR"), "KR", FIXME),
            args(new Locale("ja", "JP"), "JP"),
            args(new Locale("lv", "LV"), "LV"),
            args(new Locale("mk", "MK"), "MK", 0),
            args(new Locale("ca", "IT"), "IT", FIXME),
            args(new Locale("nl", "NL"), "NL"),
            args(new Locale("pl", "PL"), "PL"),
            args(new Locale("pt", "PT"), "PT", FIXME),
            args(new Locale("ro", "MD"), "MD", 0),
            args(new Locale("sq", "AL"), "AL", 0),
            args(new Locale("zh", "CN"), "CN"),
            args(new Locale("zh", "TW"), "TW", FIXME),
            args(new Locale("uk", "UA"), "UA"),
            args(new Locale("tr", "TR"), "TR"),
            args(new Locale("en", "SG"), "SG", FIXME),
            args(new Locale("en", "PH"), "PH"),
            args(new Locale("en", "UG"), "UG"),
            args(new Locale("en", "ZA"), "ZA"),
            args(new Locale("sv"), "SE"),
            args(new Locale("th"), "TH"),
            args(new Locale("sk"), "SK", FIXME),
            args(new Locale("ru"), "RU", 0),
            args(new Locale("pt", "BR"), "BR"),
            args(new Locale("es", "AR"), "AR", FIXME),
            args(new Locale("es", "MX"), "MX"),
            args(new Locale("es", "PY"), "PY"),
            args(new Locale("es"), "ES"),
            args(new Locale("fr", "CA"), "CA", FIXME),
            args(new Locale("fr"), "FR"),
            args(new Locale("he"), "IL"),
            args(new Locale("hr"), "HR"),
            args(new Locale("hy"), "AM"),
            args(new Locale("id"), "ID"),
            args(Locale.ENGLISH, "US"),
            args(new Locale("nb", "NO"), "NO"),
            args(new Locale("no", "NO"), "NO", FIXME),
            args(new Locale("da", "DK"), "DK"),
            args(new Locale("vi", "VI"), "VI", FIXME),
            args(new Locale("fr", "CH"), "CH", FIXME)
        );
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

    @RepeatedTest(10)
    void cellPhone_estonia() {
        String cellPhone = ESTONIAN.phoneNumber().cellPhone();
        assertThatPhone(cellPhone).matches("[58]\\d{2,3} \\d{4}");
    }

    @RepeatedTest(10)
    void phoneNumberInternational_estonia() {
        String cellPhone = ESTONIAN.phoneNumber().phoneNumberInternational();
        assertThatPhone(cellPhone).matches("\\+372 5\\d \\d{2} \\d{2} \\d{2}");
    }

    @RepeatedTest(10)
    void cellPhone_moldova() {
        String phone = MOLDOVAN.phoneNumber().cellPhone().replaceAll("\\s", "");
        assertThatPhone(phone).matches("0[567]\\d{7}");
    }

    @RepeatedTest(10)
    void phoneNumber_moldova() {
        String phone = MOLDOVAN.phoneNumber().phoneNumber().replaceAll("\\s+", "");
        assertThatPhone(phone).matches("0\\d{8}");
    }

    @RepeatedTest(10)
    void phoneNumberInternational_moldova() {
        String phone = MOLDOVAN.phoneNumber().phoneNumberInternational().replaceAll("\\s+", "");
        assertThatPhone(phone).matches("\\+373\\d{8}");
    }

    private static AbstractStringAssert<?> assertThatPhone(String phoneNumber) {
        return assertThat(phoneNumber)
            .as(() -> "Phone: %s".formatted(phoneNumber));
    }
}
