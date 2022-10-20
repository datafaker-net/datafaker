package net.datafaker.providers.videogame;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StarCraftTest extends VideoGameFakerTest {
    private final String noLeadingTrailingWhitespaceRegex = "^(?! )[-A-Za-z\\d' ]*(?<! )$";

    @Test
    void unit() {
        String unit = faker.starCraft().unit();
        assertThat(unit)
            .isNotEmpty()
            .matches(noLeadingTrailingWhitespaceRegex);
    }

    @RepeatedTest(1000)
    void unitOneThousand() {
        String unit = faker.starCraft().unit();
        // System.out.println(unit);
        assertThat(unit)
            .isNotEmpty()
            .matches(noLeadingTrailingWhitespaceRegex);
    }

    @Test
    void building() {
        String building = faker.starCraft().building();
        assertThat(building)
            .isNotEmpty()
            .matches(noLeadingTrailingWhitespaceRegex);
    }

    @Test
    void character() {
        String character = faker.starCraft().character();
        assertThat(character)
            .isNotEmpty()
            .matches(noLeadingTrailingWhitespaceRegex);
    }

    @Test
    void planet() {
        String planet = faker.starCraft().planet();
        assertThat(planet)
            .isNotEmpty()
            .matches(noLeadingTrailingWhitespaceRegex);
    }

    @RepeatedTest(1000)
    void planetOneThousand() {
        String planet = faker.starCraft().planet();
        assertThat(planet)
            .isNotEmpty()
            .matches(noLeadingTrailingWhitespaceRegex);
    }

}
