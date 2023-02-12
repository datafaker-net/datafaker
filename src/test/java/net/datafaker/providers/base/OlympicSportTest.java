package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class OlympicSportTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.olympicSport().summerOlympics(), "olympic_sport.summer_olympics"),
                TestSpec.of(() -> faker.olympicSport().winterOlympics(), "olympic_sport.winter_olympics"),
                TestSpec.of(() -> faker.olympicSport().summerParalympics(), "olympic_sport.summer_paralympics"),
                TestSpec.of(() -> faker.olympicSport().winterParalympics(), "olympic_sport.winter_paralympics"),
                TestSpec.of(() -> faker.olympicSport().ancientOlympics(), "olympic_sport.ancient_olympics"),
                TestSpec.of(() -> faker.olympicSport().unusual(), "olympic_sport.unusual"));
    }

}
