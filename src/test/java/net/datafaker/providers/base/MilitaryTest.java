package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class MilitaryTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Military military = faker.military();
        return List.of(TestSpec.of(military::armyRank, "military.army_rank"),
                TestSpec.of(military::marinesRank, "military.marines_rank"),
                TestSpec.of(military::navyRank, "military.navy_rank"),
                TestSpec.of(military::airForceRank, "military.air_force_rank"),
                TestSpec.of(military::dodPaygrade, "military.dod_paygrade"));
    }

}
