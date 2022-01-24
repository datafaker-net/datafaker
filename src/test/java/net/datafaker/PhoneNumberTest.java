package net.datafaker;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import net.datafaker.repeating.Repeat;
import org.junit.Test;

import java.util.Locale;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class PhoneNumberTest extends AbstractFakerTest {

    private final PhoneNumberUtil util = PhoneNumberUtil.getInstance();


    @Test
    public void testCellPhone_enUS() {
        final Faker f = new Faker(Locale.US);
        assertThat(f.phoneNumber().cellPhone(), matchesRegularExpression("\\(?\\d+\\)?([- .]\\d+){1,3}"));
    }

    final Faker usfaker = new Faker(new Locale("en_US"));

    @Test
    @Repeat(times = 1000)
    public void testAllCellPhone_enUS() throws NumberParseException {
        String phoneNumber = usfaker.phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "US");
        assertTrue(util.isValidNumberForRegion(proto, "US"));
    }

    final Faker sefaker = new Faker(new Locale("sv_SE"));

    @Test
    @Repeat(times = 1000)
    public void testAllCellPhone_svSE() throws NumberParseException {
        String phoneNumber = sefaker.phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "SE");
        assertTrue(phoneNumber, util.isValidNumberForRegion(proto, "SE"));
    }

    final Faker czfaker = new Faker(new Locale("cs_CZ"));

    @Test
    @Repeat(times = 1000)
    public void testAllCellPhone_csCZ() throws NumberParseException {
        String phoneNumber = czfaker.phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "CZ");
        assertTrue(phoneNumber, util.isValidNumberForRegion(proto, "CZ"));
    }

    final Faker nofaker = new Faker(new Locale("nb_NO"));

    @Test
    public void testAllCellPhone_nbNO() throws NumberParseException {
        int errorCount = 0;

        for(int i= 0; i < 1000; i++) {
            String phoneNumber = nofaker.phoneNumber().phoneNumber();
            Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NO");

            if (!util.isValidNumberForRegion(proto, "NO")) {
                errorCount ++;
            }
        }

        // Not perfect yet, but should be good enough
        assertTrue(errorCount < 250);
    }

    final Faker nlfaker = new Faker(new Locale("nl_NL"));

    @Test
    @Repeat(times = 1000)
    public void testAllCellPhone_nl() throws NumberParseException {
        String phoneNumber = nlfaker.phoneNumber().phoneNumber();
        Phonenumber.PhoneNumber proto = util.parse(phoneNumber, "NL");
        assertTrue(phoneNumber, util.isValidNumberForRegion(proto, "NL"));
    }

    @Test
    public void testPhone_esMx() {
        final Faker f = new Faker(new Locale("es_MX"));
        for (int i = 0; i < 100; i++) {
            assertThat(f.phoneNumber().cellPhone(), matchesRegularExpression("(044 )?\\(?\\d+\\)?([- .]\\d+){1,3}"));
            assertThat(f.phoneNumber().phoneNumber(), matchesRegularExpression("\\(?\\d+\\)?([- .]\\d+){1,3}"));
        }
    }

    @Test
    public void testPhone_CA() {
        final Locale[] locales = new Locale[]{Locale.CANADA, new Locale("ca")};
        for (Locale locale : locales) {
            final Faker f = new Faker(locale);
            final String canadianAreaCode = "403|587|780|825|236|250|604|672|778|204|431|506|"
                    + "709|782|902|226|249|289|343|365|416|437|519|548|613|647|705|807|905|367|"
                    + "418|438|450|514|579|581|819|873|306|639|867";
            for (int i = 0; i < 100; i++) {
                assertThat(f.phoneNumber().cellPhone(),
                        matchesRegularExpression(
                                String.format("((1-)?(\\(?(%s)\\)?)|(%s))[- .]\\d{3}[- .]\\d{4}",
                                        canadianAreaCode, canadianAreaCode)));
            }
        }
    }

    @Test
    public void testCellPhone() {
        assertThat(faker.phoneNumber().cellPhone(), matchesRegularExpression("\\(?\\d+\\)?([- .]\\d+){1,3}"));
    }

    @Test
    public void testPhoneNumber() {
        assertThat(faker.phoneNumber().phoneNumber(), matchesRegularExpression("\\(?\\d+\\)?([- .]x?\\d+){1,5}"));
    }

    @Test
    public void testExtension() {
        assertThat(faker.phoneNumber().extension(), matchesRegularExpression("\\d{4}"));
    }

    @Test
    public void testSubscriberNumber() {
        assertThat(faker.phoneNumber().subscriberNumber(), matchesRegularExpression("\\d{4}"));
    }

    @Test
    public void testSubscriberNumberWithLength() {
        assertThat(faker.phoneNumber().subscriberNumber(10), matchesRegularExpression("\\d{10}"));
    }
}
