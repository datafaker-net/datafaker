package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class ConstructionTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Construction construction = faker.construction();
        return List.of(TestSpec.of(construction::heavyEquipment, "construction.heavy_equipment"),
                TestSpec.of(construction::materials, "construction.materials"),
                TestSpec.of(construction::subcontractCategories, "construction.subcontract_categories"),
                TestSpec.of(construction::roles, "construction.roles"),
                TestSpec.of(construction::trades, "construction.trades"),
                TestSpec.of(construction::standardCostCodes, "construction.standard_cost_codes"));
    }
}
