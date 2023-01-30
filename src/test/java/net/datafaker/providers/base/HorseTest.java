package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class HorseTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.horse().name(), "creature.horse.name"),
                TestSpec.of(() -> faker.horse().breed(), "creature.horse.breed"));
    }

}
