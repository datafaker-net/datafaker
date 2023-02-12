package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class HorseTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.horse().name(), "creature.horse.name"),
                TestSpec.of(() -> faker.horse().breed(), "creature.horse.breed"));
    }

}
