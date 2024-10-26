package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 2.3.0
 */
public class Medication extends AbstractProvider<HealthcareProviders> {
    protected Medication(HealthcareProviders faker) {
        super(faker);
    }

    public String drugName() {
        return resolve("healthcare.medication.drug_name");
    }
}
