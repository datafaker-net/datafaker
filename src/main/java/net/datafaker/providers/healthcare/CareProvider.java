package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 2.3.0
 */
public class CareProvider extends AbstractProvider<HealthcareProviders> {
    protected CareProvider(HealthcareProviders faker) {
        super(faker);
    }

    public String hospitalName() {
        return resolve("healthcare.care_provider.hospital_name");
    }

    public String medicalProfession() {
        return resolve("healthcare.care_provider.medical_profession");
    }
}
