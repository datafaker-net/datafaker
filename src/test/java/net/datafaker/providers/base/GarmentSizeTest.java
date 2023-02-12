package net.datafaker.providers.base;


import java.util.List;
import java.util.Collection;

public class GarmentSizeTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        GarmentSize garmetSize = faker.garmentSize();
        return List.of(TestSpec.of(garmetSize::size, "garments_sizes.sizes"));
    }

}
