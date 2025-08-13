package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class CatTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Cat cat = faker.cat();
        return List.of(TestSpec.of(cat::name, "creature.cat.name"),
            TestSpec.of(cat::breed, "creature.cat.breed"),
            TestSpec.of(cat::registry, "creature.cat.registry"));
    }
}
