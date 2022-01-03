package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class HearthstoneTest extends AbstractFakerTest {

    @Test
    public void mainProfessionTest() {
        String profession = faker.hearthstone().mainProfession();
        assertThat(profession, matchesRegularExpression("[ A-Za-z]+"));
    }

    @Test
    public void mainCharacterTest() {
        String character = faker.hearthstone().mainCharacter();
        assertThat(character, matchesRegularExpression("[ A-Za-z']+"));
    }

    @Test
    public void mainPatternTest() {
        String pattern = faker.hearthstone().mainPattern();
        assertThat(pattern, matchesRegularExpression("[ A-Za-z]+"));
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
        assertThat(rank, matchesRegularExpression("[ A-Za-z0-9]+"));
    }

    @Test
    public void wildRankTest() {
        String rank = faker.hearthstone().wildRank();
        assertThat(rank, matchesRegularExpression("[ A-Za-z0-9]+"));
    }
}
