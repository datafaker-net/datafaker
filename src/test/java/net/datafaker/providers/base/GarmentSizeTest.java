package net.datafaker.providers.base;


import java.util.List;
import java.util.Collection;

public class GarmentSizeTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of((() -> faker.garmentSize().size()), "garments_sizes.sizes"));
    }

}
