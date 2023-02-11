package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class HorseTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Horse horse = faker.horse();
        return Arrays.asList(TestSpec.of(horse::name, "creature.horse.name"),
                TestSpec.of(horse::breed, "creature.horse.breed"));
    }

}
