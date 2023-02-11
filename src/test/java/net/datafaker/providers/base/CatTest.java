package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class CatTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Cat cat = faker.cat();
        return Arrays.asList(TestSpec.of(cat::name, "creature.cat.name"),
            TestSpec.of(cat::breed, "creature.cat.breed"),
            TestSpec.of(cat::registry, "creature.cat.registry"));
    }
}
