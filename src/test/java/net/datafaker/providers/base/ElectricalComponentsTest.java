package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class ElectricalComponentsTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        ElectricalComponents electricalComponents = faker.electricalComponents();
        return Arrays.asList(TestSpec.of(electricalComponents::active, "electrical_components.active"),
            TestSpec.of(electricalComponents::passive, "electrical_components.passive"),
            TestSpec.of(electricalComponents::electromechanical, "electrical_components.electromechanical"));
    }
}
