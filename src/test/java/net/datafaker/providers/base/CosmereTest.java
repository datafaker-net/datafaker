package net.datafaker.providers.base;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CosmereTest extends net.datafaker.AbstractFakerTest {

    @Test
    void aons() {
        assertThat(faker.cosmere().aons()).isNotEmpty();
    }

    @Test
    void shardWorlds() {
        assertThat(faker.cosmere().shardWorlds()).isNotEmpty();
    }

    @Test
    void shards() {
        assertThat(faker.cosmere().shards()).isNotEmpty();
    }

    @Test
    void surges() {
        assertThat(faker.cosmere().surges()).isNotEmpty();
    }

    @Test
    void knightsRadiant() {
        assertThat(faker.cosmere().knightsRadiant()).isNotEmpty();
    }

    @Test
    void metals() {
        assertThat(faker.cosmere().metals()).isNotEmpty();
    }

    @Test
    void allomancers() {
        assertThat(faker.cosmere().allomancers()).isNotEmpty();
    }

    @Test
    void feruchemists() {
        assertThat(faker.cosmere().feruchemists()).isNotEmpty();
    }

    @Test
    void heralds() {
        assertThat(faker.cosmere().heralds()).isNotEmpty();
    }

    @Test
    void sprens() {
        assertThat(faker.cosmere().sprens()).isNotEmpty();
    }

}
