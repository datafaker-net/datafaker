package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class MedicalTest extends AbstractFakerTest {

    @Test
    public void testMedicineName() {
        assertThat(faker.medical().medicineName()).isNotEmpty();
    }

    @Test
    public void testDiseaseName() {
        assertThat(faker.medical().diseaseName()).isNotEmpty();
    }

    @Test
    public void testHospitalName() {
        assertThat(faker.medical().hospitalName()).isNotEmpty();
    }

    @Test
    public void testSymptom() {
        assertThat(faker.medical().symptoms()).isNotEmpty();
    }

    @Test
    public void testDiagnosisCodeUS() {
        // will use icd-10-cm - https://www.johndcook.com/blog/2019/05/05/regex_icd_codes/
        Faker faker = new Faker(Locale.US);

        for (int i = 0; i < 100; i++) { // Loading the US data is slow.
            String actual = faker.medical().diagnosisCode();
            assertThat(actual).matches("[A-TV-Z][0-9][0-9AB](\\.[0-9A-TV-Z]{0,4})?");
        }
    }

    @RepeatedTest(100)
    public void testDiagnosisCodeAU() {
        // will use icd-10-am - https://ace.ihpa.gov.au/Downloads/Current/ICD-10-AM-ACHI-ACS%2011th%20Edition/Education/11th%20Edition%20PDF%20files/Coding-Exercise-Workbook-Eleventh-Edition%20V2-15%20Jun%202019.pdf
        Faker faker = new Faker(new Locale("en", "au"));

        String actual = faker.medical().diagnosisCode();
        assertThat(actual).matches("[A-Z][0-9]{1,2}\\.[0-9]{1,2}");
    }

    @RepeatedTest(100)
    public void testDiagnosisCodeNotAustraliaNorUS() {
        // will use icd-10 - variation of https://regexlib.com/REDetails.aspx?regexp_id=2276&AspxAutoDetectCookieSupport=1
        Faker faker = new Faker(Locale.FRANCE);

        String actual = faker.medical().diagnosisCode();
        assertThat(actual).matches("^[A-Z][0-9]{1,2}(\\.[0-9])?$");
    }

    @RepeatedTest(100)
    public void testProcedureCodes() {
        // will use icd-10-pcs - https://regex101.com/library/nJ1wC4
        String procedureCode = faker.medical().procedureCode();
        assertThat(procedureCode).matches("^[a-hj-np-zA-HJ-NP-Z0-9]{7}$");
    }
}
