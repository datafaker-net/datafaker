package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class MilitaryTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.military().armyRank(), "military.army_rank"),
                TestSpec.of(() -> faker.military().marinesRank(), "military.marines_rank"),
                TestSpec.of(() -> faker.military().navyRank(), "military.navy_rank"),
                TestSpec.of(() -> faker.military().airForceRank(), "military.air_force_rank"),
                TestSpec.of(() -> faker.military().dodPaygrade(), "military.dod_paygrade"));
    }

}
