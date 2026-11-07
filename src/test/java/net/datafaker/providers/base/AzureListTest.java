package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class AzureListTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(faker.azure()::region, "azure.regions"));
    }
}
