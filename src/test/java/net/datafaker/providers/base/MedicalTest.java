package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collection;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("removal")
class MedicalTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Medical medical = faker.medical();
        return List.of(TestSpec.of(medical::medicineName, "medical.medicine_name"),
                TestSpec.of(medical::diseaseName, "medical.disease_name"),
                TestSpec.of(medical::hospitalName, "medical.hospital_name"),
                TestSpec.of(medical::symptoms, "medical.symptoms"),
                TestSpec.of(medical::medicalProfession, "medical.medical_professions"));
    }

    @Test
    void testDiagnosisCodeUS() {
        // will use icd-10-cm - https://www.johndcook.com/blog/2019/05/05/regex_icd_codes/
        BaseFaker faker = new BaseFaker(Locale.US);

        for (int i = 0; i < 100; i++) { // Loading the US data is slow.
            String actual = faker.medical().diagnosisCode();
            assertThat(actual).matches("[A-TV-Z][0-9][0-9AB](\\.[0-9A-TV-Z]{0,4})?");
        }
    }

    @RepeatedTest(100)
    void testDiagnosisCodeAU() {
        // will use icd-10-am - https://ace.ihpa.gov.au/Downloads/Current/ICD-10-AM-ACHI-ACS%2011th%20Edition/Education/11th%20Edition%20PDF%20files/Coding-Exercise-Workbook-Eleventh-Edition%20V2-15%20Jun%202019.pdf
        BaseFaker faker = new BaseFaker(new Locale("en", "au"));

        String actual = faker.medical().diagnosisCode();
        assertThat(actual).matches("[A-Z][0-9]{1,2}\\.[0-9]{1,2}");
    }

    @RepeatedTest(100)
    void testDiagnosisCodeNotAustraliaNorUS() {
        // will use icd-10 - variation of https://regexlib.com/REDetails.aspx?regexp_id=2276&AspxAutoDetectCookieSupport=1
        BaseFaker faker = new BaseFaker(Locale.FRANCE);

        String actual = faker.medical().diagnosisCode();
        assertThat(actual).matches("^[A-Z][0-9]{1,2}(\\.[0-9])?$");
    }

    @RepeatedTest(100)
    void testProcedureCodes() {
        // will use icd-10-pcs - https://regex101.com/library/nJ1wC4
        String procedureCode = faker.medical().procedureCode();
        assertThat(procedureCode).matches("^[a-hj-np-zA-HJ-NP-Z0-9]{7}$");
    }
}
