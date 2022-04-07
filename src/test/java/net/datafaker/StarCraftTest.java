package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StarCraftTest extends AbstractFakerTest {
    private final String noLeadingTrailingWhitespaceRegex = "^(?! )[-A-Za-z0-9' ]*(?<! )$";

    @Test
    public void testUnit() {
        String unit = faker.starCraft().unit();
        assertThat(unit).isNotEmpty();
        assertThat(unit).matches(noLeadingTrailingWhitespaceRegex);
    }

    @RepeatedTest(1000)
    public void testUnitOneThousand() {
        String unit = faker.starCraft().unit();
        // System.out.println(unit);
        assertThat(unit).isNotEmpty();
        assertThat(unit).matches(noLeadingTrailingWhitespaceRegex);
    }

    @Test
    public void testBuilding() {
        String building = faker.starCraft().building();
        assertThat(building).isNotEmpty();
        assertThat(building).matches(noLeadingTrailingWhitespaceRegex);
    }

    @Test
    public void testCharacter() {
        String character = faker.starCraft().character();
        assertThat(character).isNotEmpty();
        assertThat(character).matches(noLeadingTrailingWhitespaceRegex);
    }

    @Test
    public void testPlanet() {
        String planet = faker.starCraft().planet();
        assertThat(planet).isNotEmpty();
        assertThat(planet).matches(noLeadingTrailingWhitespaceRegex);
    }

    @RepeatedTest(1000)
    public void testPlanetOneThousand() {
        String planet = faker.starCraft().planet();
        // System.out.println(planet);
        assertThat(planet).isNotEmpty();
        assertThat(planet).matches(noLeadingTrailingWhitespaceRegex);
    }

}
