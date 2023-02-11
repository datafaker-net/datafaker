package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class BrandTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Brand brand = faker.brand();
        return Arrays.asList(
            TestSpec.of(brand::sport, "brand.sport"),
            TestSpec.of(brand::car, "vehicle.makes"),
            TestSpec.of(brand::watch, "brand.watch")
        );
    }
}
