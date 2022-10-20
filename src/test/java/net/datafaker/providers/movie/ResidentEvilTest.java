package net.datafaker.providers.movie;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResidentEvilTest extends MovieFakerTest {

    static private final String WORDS_WITH_SPECIAL_CHAR_REGEX = "^(?! )[A-Za-z0-9αγβ'.()\\- ]*(?<! )$";

    @Test
    void character() {
        String character = faker.residentEvil().character();
        assertThat(character)
            .isNotEmpty()
            .matches(WORDS_WITH_SPECIAL_CHAR_REGEX);
    }

    @Test
    void biologicalAgent() {
        String biologicalAgent = faker.residentEvil().biologicalAgent();
        assertThat(biologicalAgent)
            .isNotEmpty()
            .matches(WORDS_WITH_SPECIAL_CHAR_REGEX);
    }

    @Test
    void equipment() {
        String equipment = faker.residentEvil().equipment();
        assertThat(equipment)
            .isNotEmpty()
            .matches(WORDS_WITH_SPECIAL_CHAR_REGEX);
    }

    @Test
    void location() {
        String location = faker.residentEvil().location();
        assertThat(location)
            .isNotEmpty()
            .matches(WORDS_WITH_SPECIAL_CHAR_REGEX);
    }

    @Test
    void creature() {
        String creature = faker.residentEvil().creature();
        assertThat(creature)
            .isNotEmpty()
            .matches(WORDS_WITH_SPECIAL_CHAR_REGEX);
    }

}
