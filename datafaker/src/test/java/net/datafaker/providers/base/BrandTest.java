package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class BrandTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Brand brand = faker.brand();
        return List.of(
            TestSpec.of(brand::sport, "brand.sport"),
            TestSpec.of(brand::car, "vehicle.makes"),
            TestSpec.of(brand::watch, "brand.watch")
        );
    }
}
