package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class HouseTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        House house = faker.house();
        return List.of(TestSpec.of(house::furniture, "house.furniture"),
            TestSpec.of(house::room, "house.rooms"));
    }
}
