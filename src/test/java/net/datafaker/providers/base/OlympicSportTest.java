package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class OlympicSportTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        OlympicSport olympicSport = faker.olympicSport();
        return List.of(TestSpec.of(olympicSport::summerOlympics, "olympic_sport.summer_olympics"),
                TestSpec.of(olympicSport::winterOlympics, "olympic_sport.winter_olympics"),
                TestSpec.of(olympicSport::summerParalympics, "olympic_sport.summer_paralympics"),
                TestSpec.of(olympicSport::winterParalympics, "olympic_sport.winter_paralympics"),
                TestSpec.of(olympicSport::ancientOlympics, "olympic_sport.ancient_olympics"),
                TestSpec.of(olympicSport::unusual, "olympic_sport.unusual"));
    }

}
