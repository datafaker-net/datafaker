package net.datafaker.providers.healthcare;

import net.datafaker.providers.base.ProviderRegistration;

public interface HealthcareProviders extends ProviderRegistration {

    default CareProvider careProvider() {
        return getProvider(CareProvider.class, CareProvider::new);
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
