package net.datafaker.providers.base;


import java.util.List;
import java.util.Collection;

class CosmereTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.cosmere().aons(), "cosmere.aons"),
                TestSpec.of(() -> faker.cosmere().shardWorlds(), "cosmere.shard_worlds"),
                TestSpec.of(() -> faker.cosmere().shards(), "cosmere.shards"),
                TestSpec.of(() -> faker.cosmere().surges(), "cosmere.surges"),
                TestSpec.of(() -> faker.cosmere().knightsRadiant(), "cosmere.knights_radiant"),
                TestSpec.of(() -> faker.cosmere().metals(), "cosmere.metals"),
                TestSpec.of(() -> faker.cosmere().allomancers(), "cosmere.allomancers"),
                TestSpec.of(() -> faker.cosmere().feruchemists(), "cosmere.feruchemists"),
                TestSpec.of(() -> faker.cosmere().heralds(), "cosmere.heralds"),
                TestSpec.of(() -> faker.cosmere().sprens(), "cosmere.sprens")
                );
    }

}
