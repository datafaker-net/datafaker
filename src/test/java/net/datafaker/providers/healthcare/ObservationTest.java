package net.datafaker.providers.healthcare;

import java.util.Collection;
import java.util.List;

class ObservationTest extends HealthcareFakerTest {

    private final Observation observation = getFaker().observation();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(observation::symptom, "healthcare.observation.symptom")
        );
    }
}
