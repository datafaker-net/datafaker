package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HearthstoneTest extends AbstractFakerTest {

    @Test
    public void mainProfessionTest() {
        String profession = faker.hearthstone().mainProfession();
        assertThat(profession).matches("[ A-Za-z]+");
    }

    @Test
    public void mainCharacterTest() {
        String character = faker.hearthstone().mainCharacter();
        assertThat(character).matches("[ A-Za-z']+");
    }

    @Test
    public void mainPatternTest() {
        String pattern = faker.hearthstone().mainPattern();
        assertThat(pattern).matches("[ A-Za-z]+");
    }

    @Test
    public void battlegroundsScoreTest() {
        int score = faker.hearthstone().battlegroundsScore();
        assertTrue(score <= 16000);
        assertTrue(score >= 0);
    }

    @Test
    public void standardRankTest() {
        String rank = faker.hearthstone().standardRank();
        assertThat(rank).matches("[ A-Za-z0-9]+");
    }

    @Test
    public void wildRankTest() {
        String rank = faker.hearthstone().wildRank();
        assertThat(rank).matches("[ A-Za-z0-9]+");
    }
}
