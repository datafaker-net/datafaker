package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.ProviderRegistration;

/**
 * @since 2.3.0
 */
public interface HealthcareProviders extends ProviderRegistration {

    default CareProvider careProvider() {
        return getProvider(CareProvider.class, CareProvider::new);
    }

    default Disease disease() {
        return getProvider(Disease.class, Disease::new);
    }

    default Medication medication() {
        return getProvider(Medication.class, Medication::new);
    }

    default MedicalProcedure medicalProcedure() {
        return getProvider(MedicalProcedure.class, MedicalProcedure::new);
    }

    default Observation observation() {
        return getProvider(Observation.class, Observation::new);
    }
}
