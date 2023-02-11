package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class ApplianceTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Appliance appliance = faker.appliance();
        return Arrays.asList(TestSpec.of(appliance::brand, "appliance.brand", "[A-Za-z .-]+"),
                TestSpec.of(appliance::equipment, "appliance.equipment"));
    }
}
