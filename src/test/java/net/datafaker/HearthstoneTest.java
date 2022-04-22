package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(score).isLessThanOrEqualTo(16000);
        assertThat(score).isGreaterThanOrEqualTo(0);
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
