package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Generate random medical procedures concepts
 */
public class MedicalProcedure extends AbstractProvider<HealthcareProviders> {
    protected MedicalProcedure(HealthcareProviders faker) {
        super(faker);
    }

    public String icd10() {
        String regex = resolve("healthcare.medical_procedure.icd10");
        return faker.regexify(regex);
    }
}
