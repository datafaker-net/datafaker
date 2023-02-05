package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class BrandTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(() -> faker.brand().sport(), "brand.sport"),
            TestSpec.of(() -> faker.brand().car(), "vehicle.makes"),
            TestSpec.of(() -> faker.brand().watch(), "brand.watch")
        );
    }
}
