package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Generate random medical observation concepts, like laboratory results, symptoms, vital signs etc...
 *
 * @since 2.3.0
 */
public class Observation extends AbstractProvider<HealthcareProviders> {
    protected Observation(HealthcareProviders faker) {
        super(faker);
    }

    public String symptom() {
        return resolve("healthcare.observation.symptom");
    }
}
