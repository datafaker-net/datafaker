package net.datafaker.base;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

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
        String phoneNumber = new BaseFaker(new Locale("en_US")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "US");
        assertThat(util.isValidNumberForRegion(proto, "US")).as(phoneNumber).isTrue();
    }


    @RepeatedTest(10)
    void testAllCellPhone_svSE() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("sv_SE")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "SE");
        assertThat(util.isValidNumberForRegion(proto, "SE")).as(phoneNumber).isTrue();
    }


    @RepeatedTest(10)
    void testAllCellPhone_csCZ() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("cs_CZ")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CZ");
        assertThat(util.isValidNumberForRegion(proto, "CZ")).as(phoneNumber).isTrue();
    }


    @Test
    void testAllCellPhone_enGB() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        int errorCount = 0;
        final BaseFaker faker = new BaseFaker(new Locale("en_GB"));
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

        final BaseFaker faker = new BaseFaker(new Locale("nb_NO"));
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
        String phoneNumber = new BaseFaker(new Locale("nl_NL")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NL");
        assertThat(util.isValidNumberForRegion(proto, "NL")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhone_esMx() {
        final BaseFaker f = new BaseFaker(new Locale("es_MX"));
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
        String phoneNumber = new BaseFaker(new Locale("en_PH")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "PH");
        assertThat(util.isValidNumberForRegion(proto, "PH")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_US() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "US")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "US");
        assertThat(util.isValidNumberForRegion(proto, "US")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_US() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "US")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "US");
        assertThat(util.isValidNumberForRegion(proto, "US")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_en_GB() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "GB")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "GB");
        assertThat(util.isValidNumberForRegion(proto, "GB")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_en_GB() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "GB")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "GB");
        assertThat(util.isValidNumberForRegion(proto, "GB")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_bg() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("bg", "BG")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "BG");
        assertThat(util.isValidNumberForRegion(proto, "BG")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_bg() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("bg", "BG")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "BG");
        assertThat(util.isValidNumberForRegion(proto, "BG")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_by() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("by", "BY")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "BY");
        assertThat(util.isValidNumberForRegion(proto, "BY")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_by() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("by", "BY")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "BY");
        assertThat(util.isValidNumberForRegion(proto, "BY")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_ca() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("ca", "CA")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CA");
        assertThat(util.isValidNumberForRegion(proto, "CA")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_ca() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("ca", "CA")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CA");
        assertThat(util.isValidNumberForRegion(proto, "CA")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_cs_CZ() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("cs", "CZ")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CZ");
        assertThat(util.isValidNumberForRegion(proto, "CZ")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_cs_CZ() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("cs", "CZ")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CZ");
        assertThat(util.isValidNumberForRegion(proto, "CZ")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_de() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("de", "DE")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "DE");
        assertThat(util.isValidNumberForRegion(proto, "DE")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_de() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("de", "DE")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "DE");
        assertThat(util.isValidNumberForRegion(proto, "DE")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_de_AT() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("de", "AT")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "AT");
        assertThat(util.isValidNumberForRegion(proto, "AT")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_de_AT() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("de", "AT")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "AT");
        assertThat(util.isValidNumberForRegion(proto, "AT")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_de_CH() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("de", "CH")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CH");
        assertThat(util.isValidNumberForRegion(proto, "CH")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_de_CH() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("de", "CH")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CH");
        assertThat(util.isValidNumberForRegion(proto, "CH")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_en_AU() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "AU")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "AU");
        assertThat(util.isValidNumberForRegion(proto, "AU")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_en_AU() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "AU")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "AU");
        assertThat(util.isValidNumberForRegion(proto, "AU")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_en_CA() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "CA")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CA");
        assertThat(util.isValidNumberForRegion(proto, "CA")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_en_CA() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "CA")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CA");
        assertThat(util.isValidNumberForRegion(proto, "CA")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_en_IND() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "IND")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "IN");
        assertThat(util.isValidNumberForRegion(proto, "IN")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_en_IND() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "IND")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "IN");
        assertThat(util.isValidNumberForRegion(proto, "IN")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_en_MS() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "MS")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "MS");
        assertThat(util.isValidNumberForRegion(proto, "MS")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_en_MS() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "MS")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "MS");
        assertThat(util.isValidNumberForRegion(proto, "MS")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_en_NEP() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "NEP")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NP");
        assertThat(util.isValidNumberForRegion(proto, "NP")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_en_NEP() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "NEP")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NP");
        assertThat(util.isValidNumberForRegion(proto, "NP")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_en_NG() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "NG")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NG");
        assertThat(util.isValidNumberForRegion(proto, "NG")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_en_NG() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "NG")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NG");
        assertThat(util.isValidNumberForRegion(proto, "NG")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_en_NZ() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "NZ")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NZ");
        assertThat(util.isValidNumberForRegion(proto, "NZ")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_en_NZ() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "NZ")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NZ");
        assertThat(util.isValidNumberForRegion(proto, "NZ")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_en_PAK() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "PAK")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "PK");
        assertThat(util.isValidNumberForRegion(proto, "PK")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_en_PAK() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("en", "PAK")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "PK");
        assertThat(util.isValidNumberForRegion(proto, "PK")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_hu() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("hu", "HU")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "HU");
        assertThat(util.isValidNumberForRegion(proto, "HU")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_hu() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("hu", "HU")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "HU");
        assertThat(util.isValidNumberForRegion(proto, "HU")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_fi_FI() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("fi", "FI")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "FI");
        assertThat(util.isValidNumberForRegion(proto, "FI")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_fi_FI() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("fi", "FI")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "FI");
        assertThat(util.isValidNumberForRegion(proto, "FI")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_ko() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("ko", "KR")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "KR");
        assertThat(util.isValidNumberForRegion(proto, "KR")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_ko() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("ko", "KR")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "KR");
        assertThat(util.isValidNumberForRegion(proto, "KR")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_ja() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("ja", "JP")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "JP");
        assertThat(util.isValidNumberForRegion(proto, "JP")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_ja() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("ja", "JP")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "JP");
        assertThat(util.isValidNumberForRegion(proto, "JP")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_lv() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("lv", "LV")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "LV");
        assertThat(util.isValidNumberForRegion(proto, "LV")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_lv() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("lv", "LV")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "LV");
        assertThat(util.isValidNumberForRegion(proto, "LV")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_it_IT() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("it", "IT")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "IT");
        assertThat(util.isValidNumberForRegion(proto, "IT")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_it_IT() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("it", "IT")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "IT");
        assertThat(util.isValidNumberForRegion(proto, "IT")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_nl_NL() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("nl", "NL")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NL");
        assertThat(util.isValidNumberForRegion(proto, "NL")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_nl_NL() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("nl", "NL")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NL");
        assertThat(util.isValidNumberForRegion(proto, "NL")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_pl_PL() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("pl", "PL")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "PL");
        assertThat(util.isValidNumberForRegion(proto, "PL")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_pl_PL() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("pl", "PL")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "PL");
        assertThat(util.isValidNumberForRegion(proto, "PL")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_pt_PT() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("pt", "PT")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "PT");
        assertThat(util.isValidNumberForRegion(proto, "PT")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_pt_PT() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("pt", "PT")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "PT");
        assertThat(util.isValidNumberForRegion(proto, "PT")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_zh_CN() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("zh", "CN")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CN");
        assertThat(util.isValidNumberForRegion(proto, "CN")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_zh_CN() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("zh", "CN")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CN");
        assertThat(util.isValidNumberForRegion(proto, "CN")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_zh_TW() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("zh", "TW")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "TW");
        assertThat(util.isValidNumberForRegion(proto, "TW")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_zh_TW() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("zh", "TW")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "TW");
        assertThat(util.isValidNumberForRegion(proto, "TW")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_uk_UA() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("uk", "UA")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "UA");
        assertThat(util.isValidNumberForRegion(proto, "UA")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_uk_UA() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("uk", "UA")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "UA");
        assertThat(util.isValidNumberForRegion(proto, "UA")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberNational_tr() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("tr", "TR")).phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "TR");
        assertThat(util.isValidNumberForRegion(proto, "TR")).as(phoneNumber).isTrue();
    }

    @Test
    void testPhoneNumberInternational_tr() throws NumberParseException {
        final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
        String phoneNumber = new BaseFaker(new Locale("tr", "TR")).phoneNumber().phoneNumberInternational();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "TR");
        assertThat(util.isValidNumberForRegion(proto, "TR")).as(phoneNumber).isTrue();
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
