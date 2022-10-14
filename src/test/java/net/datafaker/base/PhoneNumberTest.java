package net.datafaker.base;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class PhoneNumberTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testCellPhone_enUS() {
        final BaseFaker f = new BaseFaker(Locale.US);
        String cellPhone = f.phoneNumber().cellPhone();
        assertThat(cellPhone).matches("\\(?\\d+\\)?([- .]\\d+){1,3}");
    }

    @RepeatedTest(10)
    void testAllCellPhone_enUS() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "US")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "US");
        assertThat(util.isValidNumberForRegion(proto, "US")).as(phoneNumber).isTrue();
    }


    @RepeatedTest(10)
    void testAllCellPhone_svSE() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("sv", "SE")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "SE");
        assertThat(util.isValidNumberForRegion(proto, "SE")).as(phoneNumber).isTrue();
    }


    @RepeatedTest(10)
    void testAllCellPhone_csCZ() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("cs", "CZ")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CZ");
        assertThat(util.isValidNumberForRegion(proto, "CZ")).as(phoneNumber).isTrue();
    }


    @Test
    void testAllCellPhone_enGB() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        int errorCount = 0;
        final BaseFaker faker = new BaseFaker(new Locale("en", "GB"));
        for (int i = 0; i < 10; i++) {
            String phoneNumber = faker.phoneNumber().phoneNumber();
            Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "GB");
            if (!util.isValidNumberForRegion(proto, "GB")) {
                errorCount++;
            }
        }

        // Current score is ~420. Improvements are welcome.
        assertThat(errorCount).isLessThan(500);
    }


    @Test
    void testAllCellPhone_nbNO() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        int errorCount = 0;

        final BaseFaker faker = new BaseFaker(new Locale("nb", "NO"));
        for (int i = 0; i < 10; i++) {
            String phoneNumber = faker.phoneNumber().phoneNumber();
            Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NO");

            if (!util.isValidNumberForRegion(proto, "NO")) {
                errorCount++;
            }
        }

        // Not perfect yet, but should be good enough
        assertThat(errorCount).isLessThan(250);
    }


    @RepeatedTest(10)
    void testAllCellPhone_nl() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("nl", "NL")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NL");
        assertThat(util.isValidNumberForRegion(proto, "NL")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhone_esMx() {
        final BaseFaker f = new BaseFaker(new Locale("es", "MX"));
        for (int i = 0; i < 10; i++) {
            assertThat(f.phoneNumber().cellPhone()).matches("(044 )?\\(?\\d+\\)?([- .]\\d+){1,3}");
            assertThat(f.phoneNumber().phoneNumber()).matches("\\(?\\d+\\)?([- .]\\d+){1,3}");
        }
    }

    @Test
    void testPhone_CA() {
        final Locale[] locales = new Locale[]{Locale.CANADA, new Locale("ca")};
        for (Locale locale : locales) {
            final BaseFaker f = new BaseFaker(locale);
            final String canadianAreaCode = "403|587|780|825|236|250|604|672|778|204|431|506|"
                + "709|782|902|226|249|289|343|365|416|437|519|548|613|647|705|807|905|367|"
                + "418|438|450|514|579|581|819|873|306|639|867";
            for (int i = 0; i < 100; i++) {
                assertThat(f.phoneNumber().cellPhone()).matches(
                    String.format("((1-)?(\\(?(%s)\\)?)|(%s))[- .]\\d{3}[- .]\\d{4}",
                        canadianAreaCode, canadianAreaCode));
            }
        }
    }

    @RepeatedTest(10)
    void testAllCellPhone_enPh() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "PH")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "PH");
        assertThat(util.isValidNumberForRegion(proto, "PH")).as(phoneNumber).isTrue();
    }

    @ParameterizedTest
    @MethodSource("generateLanguageAndRegionOfLocales")
    void testAllPhoneNumberNational(Locale locale, String phoneNumberRegion) throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        int errorCount = 0;
        final BaseFaker faker = new BaseFaker(locale);
        for (int i = 0; i < 10; i++) {
            String phoneNumber = faker.phoneNumber().phoneNumber();
            Phonenumber.PhoneNumber proto = util.parse(phoneNumber, phoneNumberRegion);
            if (!util.isValidNumberForRegion(proto, phoneNumberRegion)) {
                errorCount++;
            }
        }
        assertThat(errorCount).isLessThan(250);
    }
    @ParameterizedTest
    @MethodSource("generateLanguageAndRegionOfLocales")
    void testAllPhoneNumberInternational(Locale locale, String phoneNumberRegion) throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        int errorCount = 0;
        final BaseFaker faker = new BaseFaker(locale);
        for (int i = 0; i < 10; i++) {
            String phoneNumber = faker.phoneNumber().phoneNumberInternational();
            Phonenumber.PhoneNumber proto = util.parse(phoneNumber, phoneNumberRegion);
            if (!util.isValidNumberForRegion(proto, phoneNumberRegion)) {
                errorCount++;
            }
        }
        assertThat(errorCount).isLessThan(250);
    }
    // `new Locale("en", "IND")` in `new Locale("en", "IND"), "IN")` is a Java's Locale
    // `"IN"` in `new Locale("en", "IND"), "IN")` is a PhoneNumberUtil's region
    private static Stream<Arguments> generateLanguageAndRegionOfLocales() {
        return Stream.of(
            Arguments.of(new Locale("en", "US"), "US"),
            Arguments.of(new Locale("en", "GB"), "GB"),
            Arguments.of(new Locale("en", "AU"), "AU"),
            Arguments.of(new Locale("en", "CA"), "CA"),
            Arguments.of(new Locale("en", "MS"), "MS"),
            Arguments.of(new Locale("en", "NG"), "NG"),
            Arguments.of(new Locale("en", "NZ"), "NZ"),
            Arguments.of(new Locale("bg", "BG"), "BG"),
            Arguments.of(new Locale("by", "BY"), "BY"),
            Arguments.of(new Locale("ca", "CA"), "CA"),
            Arguments.of(new Locale("cs", "CZ"), "CZ"),
            Arguments.of(new Locale("de", "DE"), "DE"),
            Arguments.of(new Locale("de", "AT"), "AT"),
            Arguments.of(new Locale("de", "CH"), "CH"),
            Arguments.of(new Locale("en", "IND"), "IN"),
            Arguments.of(new Locale("en", "NEP"), "NP"),
            Arguments.of(new Locale("en", "PAK"), "PK"),
            Arguments.of(new Locale("hu", "HU"), "HU"),
            Arguments.of(new Locale("fi", "FI"), "FI"),
            Arguments.of(new Locale("ko", "KR"), "KR"),
            Arguments.of(new Locale("ja", "JP"), "JP"),
            Arguments.of(new Locale("lv", "LV"), "LV"),
            Arguments.of(new Locale("it", "IT"), "IT"),
            Arguments.of(new Locale("nl", "NL"), "NL"),
            Arguments.of(new Locale("pl", "PL"), "PL"),
            Arguments.of(new Locale("pt", "PT"), "PT"),
            Arguments.of(new Locale("zh", "CN"), "CN"),
            Arguments.of(new Locale("zh", "TW"), "TW"),
            Arguments.of(new Locale("uk", "UA"), "UA"),
            Arguments.of(new Locale("tr", "TR"), "TR"),
            Arguments.of(new Locale("en", "SG"), "SG"),
            Arguments.of(new Locale("en", "PH"), "PH"),
            Arguments.of(new Locale("en", "UG"), "UG"),
            Arguments.of(new Locale("en", "ZA"), "ZA")
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
}
