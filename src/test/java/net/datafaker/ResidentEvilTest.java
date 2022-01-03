package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class ResidentEvilTest extends AbstractFakerTest {

    static private final String WORDS_WITH_SPECIAL_CHAR_REGEX = "^(?! )[A-Za-z0-9αγβ'.()\\- ]*(?<! )$";

    @Test
    public void testCharacter() {
        String character = faker.residentEvil().character();
        assertThat(character, not(is(emptyOrNullString())));
        assertThat(character, matchesRegularExpression(WORDS_WITH_SPECIAL_CHAR_REGEX));
    }

    @Test
    public void testBiologicalAgent() {
        String biologicalAgent = faker.residentEvil().biologicalAgent();
        assertThat(biologicalAgent, not(is(emptyOrNullString())));
        assertThat(biologicalAgent, matchesRegularExpression(WORDS_WITH_SPECIAL_CHAR_REGEX));
    }

    @Test
    public void testEquipment() {
        String equipment = faker.residentEvil().equipment();
        assertThat(equipment, not(is(emptyOrNullString())));
        assertThat(equipment, matchesRegularExpression(WORDS_WITH_SPECIAL_CHAR_REGEX));
    }

    @Test
    public void testLocation() {
        String location = faker.residentEvil().location();
        assertThat(location, not(is(emptyOrNullString())));
        assertThat(location, matchesRegularExpression(WORDS_WITH_SPECIAL_CHAR_REGEX));
    }

    @Test
    public void testCreature() {
        String creature = faker.residentEvil().creature();
        assertThat(creature, not(is(emptyOrNullString())));
        assertThat(creature, matchesRegularExpression(WORDS_WITH_SPECIAL_CHAR_REGEX));
    }

}
