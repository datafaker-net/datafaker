package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Generate random types of health care providers
 */
public class CareProvider extends AbstractProvider<HealthcareProviders> {
    protected CareProvider(HealthcareProviders faker) {
        super(faker);
    }

    /**
     * Generate random hospital name
     * @return A hospital name
     */
    public String hospitalName() {
        return resolve("healthcare.care_provider.hospital_name");
    }

    /**
     * Generate random medical profession
     * @return A medical profession
     */
    public String medicalProfession() {
        return resolve("healthcare.care_provider.medical_profession");
    }
}
