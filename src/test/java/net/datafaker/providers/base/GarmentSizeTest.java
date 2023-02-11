package net.datafaker.providers.base;


import java.util.Arrays;
import java.util.Collection;

public class GarmentSizeTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        GarmentSize garmetSize = faker.garmentSize();
        return Arrays.asList(TestSpec.of(garmetSize::size, "garments_sizes.sizes"));
    }

}
