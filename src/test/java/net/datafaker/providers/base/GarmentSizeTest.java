package net.datafaker.providers.base;


import java.util.Arrays;
import java.util.Collection;

public class GarmentSizeTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of((() -> faker.garmentSize().size()), "garments_sizes.sizes"));
    }

}
