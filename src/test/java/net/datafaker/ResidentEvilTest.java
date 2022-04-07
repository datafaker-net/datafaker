package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResidentEvilTest extends AbstractFakerTest {

    static private final String WORDS_WITH_SPECIAL_CHAR_REGEX = "^(?! )[A-Za-z0-9αγβ'.()\\- ]*(?<! )$";

    @Test
    public void testCharacter() {
        String character = faker.residentEvil().character();
        assertThat(character).isNotEmpty();
        assertThat(character).matches(WORDS_WITH_SPECIAL_CHAR_REGEX);
    }

    @Test
    public void testBiologicalAgent() {
        String biologicalAgent = faker.residentEvil().biologicalAgent();
        assertThat(biologicalAgent).isNotEmpty();
        assertThat(biologicalAgent).matches(WORDS_WITH_SPECIAL_CHAR_REGEX);
    }

    @Test
    public void testEquipment() {
        String equipment = faker.residentEvil().equipment();
        assertThat(equipment).isNotEmpty();
        assertThat(equipment).matches(WORDS_WITH_SPECIAL_CHAR_REGEX);
    }

    @Test
    public void testLocation() {
        String location = faker.residentEvil().location();
        assertThat(location).isNotEmpty();
        assertThat(location).matches(WORDS_WITH_SPECIAL_CHAR_REGEX);
    }

    @Test
    public void testCreature() {
        String creature = faker.residentEvil().creature();
        assertThat(creature).isNotEmpty();
        assertThat(creature).matches(WORDS_WITH_SPECIAL_CHAR_REGEX);
    }

}
