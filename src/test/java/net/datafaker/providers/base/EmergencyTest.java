package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class EmergencyTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Emergency emergency = faker.emergency();
        return List.of(
            TestSpec.of(emergency::nature, "emergency.nature"),
            TestSpec.of(emergency::location, "emergency.location"),
            TestSpec.of(emergency::instruction, "emergency.instruction"));
    }

    @Test
    void shouldReturnDryRunFlag() {
        assertThat(faker.emergency().dryRun()).isIn(false, true);
    }

    @Test
    void shouldReturnEmergencyCase() {
        Emergency.EmergencyCase emergencyCase = faker.emergency().emergencyCase();
        assertThat(emergencyCase.nature()).isNotEmpty();
        assertThat(emergencyCase.location()).isNotEmpty();
        assertThat(emergencyCase.instruction()).isNotEmpty();
        assertThat(emergencyCase.dryRun()).isIn(false, true);
    }
}
