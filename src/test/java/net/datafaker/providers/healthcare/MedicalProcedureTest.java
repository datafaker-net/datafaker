package net.datafaker.providers.healthcare;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class MedicalProcedureTest {

    private final MedicalProcedure medicalProcedure = new HealthcareFaker().medicalProcedure();

    @RepeatedTest(100)
    void testProcedureCodes() {
        // will use icd-10-pcs - https://regex101.com/library/nJ1wC4
        String procedureCode = medicalProcedure.icd10();
        assertThat(procedureCode).matches("^[a-hj-np-zA-HJ-NP-Z0-9]{7}$");
    }
}
