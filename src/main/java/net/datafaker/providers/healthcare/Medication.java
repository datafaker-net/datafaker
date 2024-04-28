package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Generate random medication/drug concepts
 */
public class Medication extends AbstractProvider<HealthcareProviders> {
    protected Medication(HealthcareProviders faker) {
        super(faker);
    }

    public String drugName() {
        return resolve("healthcare.medication.drug_name");
    }
}
