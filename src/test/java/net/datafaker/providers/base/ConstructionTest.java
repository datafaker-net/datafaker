package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class ConstructionTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.construction().heavyEquipment(), "construction.heavy_equipment"),
                TestSpec.of(() -> faker.construction().materials(), "construction.materials"),
                TestSpec.of(() -> faker.construction().subcontractCategories(), "construction.subcontract_categories"),
                TestSpec.of(() -> faker.construction().roles(), "construction.roles"),
                TestSpec.of(() -> faker.construction().trades(), "construction.trades"),
                TestSpec.of(() -> faker.construction().standardCostCodes(), "construction.standard_cost_codes"));
    }
}
