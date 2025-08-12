package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

final class ApplianceTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Appliance appliance = faker.appliance();
        return List.of(TestSpec.of(appliance::brand, "appliance.brand", "[A-Za-z .-]+"),
                TestSpec.of(appliance::equipment, "appliance.equipment"));
    }
}
