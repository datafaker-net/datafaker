package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HearthstoneTest extends AbstractBaseFakerTest {

    @Test
    void mainProfessionTest() {
        String profession = faker.hearthstone().mainProfession();
        assertThat(profession).matches("[ A-Za-z]+");
    }

    @Test
    void mainCharacterTest() {
        String character = faker.hearthstone().mainCharacter();
        assertThat(character).matches("[ A-Za-z']+");
    }

    @Test
    void mainPatternTest() {
        String pattern = faker.hearthstone().mainPattern();
        assertThat(pattern).matches("[ A-Za-z]+");
    }

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
}
