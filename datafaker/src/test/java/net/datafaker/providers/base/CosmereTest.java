package net.datafaker.providers.base;


import java.util.List;
import java.util.Collection;

class CosmereTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Cosmere cosmere = faker.cosmere();
        return List.of(TestSpec.of(cosmere::aons, "cosmere.aons"),
                TestSpec.of(cosmere::shardWorlds, "cosmere.shard_worlds"),
                TestSpec.of(cosmere::shards, "cosmere.shards"),
                TestSpec.of(cosmere::surges, "cosmere.surges"),
                TestSpec.of(cosmere::knightsRadiant, "cosmere.knights_radiant"),
                TestSpec.of(cosmere::metals, "cosmere.metals"),
                TestSpec.of(cosmere::allomancers, "cosmere.allomancers"),
                TestSpec.of(cosmere::feruchemists, "cosmere.feruchemists"),
                TestSpec.of(cosmere::heralds, "cosmere.heralds"),
                TestSpec.of(cosmere::sprens, "cosmere.sprens")
                );
    }

}
