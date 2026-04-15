package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

final class EmergencyTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Emergency emergency = faker.emergency();
        return List.of(
            TestSpec.of(emergency::nature, "emergency.nature"),
            TestSpec.of(emergency::location, "emergency.location"),
            TestSpec.of(emergency::instruction, "emergency.instruction"));
    }
}
