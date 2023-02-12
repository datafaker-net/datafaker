package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class HearthstoneTest extends VideoGameFakerTest {

    @Test
    void battlegroundsScoreTest() {
        int score = faker.hearthstone().battlegroundsScore();
        assertThat(score).isBetween(0, 16000);
    }

    @Test
    void standardRankTest() {
        String rank = faker.hearthstone().standardRank();
        assertThat(rank).matches("[ A-Za-z0-9]+");
    }

    @Test
    void wildRankTest() {
        String rank = faker.hearthstone().wildRank();
        assertThat(rank).matches("[ A-Za-z0-9]+");
    }

    private final Hearthstone hearthstone = getFaker().hearthstone();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(hearthstone::mainCharacter, "games.hearthstone.characters"),
            TestSpec.of(hearthstone::mainPattern, "games.hearthstone.patterns"),
            TestSpec.of(hearthstone::mainProfession, "games.hearthstone.professions")
        );
    }
}
