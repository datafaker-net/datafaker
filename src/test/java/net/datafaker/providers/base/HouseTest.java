package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class HouseTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        House house = faker.house();
        return Arrays.asList(TestSpec.of(house::furniture, "house.furniture"),
            TestSpec.of(house::room, "house.rooms"));
    }
}
