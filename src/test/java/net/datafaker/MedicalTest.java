package net.datafaker;

import net.datafaker.repeating.Repeat;
import org.junit.Test;

import java.util.Locale;

import static net.datafaker.matchers.IsStringWithContents.isStringWithContents;
import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class MedicalTest extends AbstractFakerTest {

    @Test
    public void testMedicineName() {
        assertThat(faker.medical().medicineName(), isStringWithContents());
    }

    @Test
    public void testDiseaseName() {
        assertThat(faker.medical().diseaseName(), isStringWithContents());
    }

    @Test
    public void testHospitalName() {
        assertThat(faker.medical().hospitalName(), isStringWithContents());
    }

    @Test
    public void testSymptom() {
        assertThat(faker.medical().symptoms(), isStringWithContents());
    }

    @Test
    public void testDiagnosisCodeUS() {
        // will use icd-10-cm - https://www.johndcook.com/blog/2019/05/05/regex_icd_codes/
        Faker faker = new Faker(Locale.US);

        for (int i = 0; i < 100; i++) {
            String actual = faker.medical().diagnosisCode();
            assertThat(actual, matchesRegularExpression("[A-TV-Z][0-9][0-9AB](\\.[0-9A-TV-Z]{0,4})?"));
        }
    }

    @Test
    public void testDiagnosisCodeAU() {
        // will use icd-10-am - https://ace.ihpa.gov.au/Downloads/Current/ICD-10-AM-ACHI-ACS%2011th%20Edition/Education/11th%20Edition%20PDF%20files/Coding-Exercise-Workbook-Eleventh-Edition%20V2-15%20Jun%202019.pdf
        Faker faker = new Faker(new Locale("en", "au"));

        for (int i = 0; i < 100; i++) {
            String actual = faker.medical().diagnosisCode();
            assertThat(actual, matchesRegularExpression("[A-Z][0-9]{1,2}\\.[0-9]{1,2}"));
        }
    }

    @Test
    public void testDiagnosisCodeNotAustraliaNorUS() {
        // will use icd-10 - variation of https://regexlib.com/REDetails.aspx?regexp_id=2276&AspxAutoDetectCookieSupport=1
        Faker faker = new Faker(Locale.FRANCE);

        for (int i = 0; i < 100; i++) {
            String actual = faker.medical().diagnosisCode();
            assertThat(actual, matchesRegularExpression("^[A-Z][0-9]{1,2}(\\.[0-9])?$"));
        }
    }

    @Test
    @Repeat(times = 100)
    public void testProcedureCodes() {
        // will use icd-10-pcs - https://regex101.com/library/nJ1wC4
        String procedureCode = faker.medical().procedureCode();
        assertThat(procedureCode, matchesRegularExpression("^[a-hj-np-zA-HJ-NP-Z0-9]{7}$"));
    }
}