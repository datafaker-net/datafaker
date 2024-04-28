package net.datafaker.providers.healthcare;

import java.util.Collection;
import java.util.List;

class MedicationTest extends HealthcareFakerTest {

    private final Medication medication = getFaker().medication();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(medication::drugName, "healthcare.medication.drug_name")
        );
    }
}
