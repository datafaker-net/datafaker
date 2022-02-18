package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class StarCraftTest extends AbstractFakerTest {
    private final String noLeadingTrailingWhitespaceRegex = "^(?! )[-A-Za-z0-9' ]*(?<! )$";

    @Test
    public void testUnit() {
        String unit = faker.starCraft().unit();
        assertThat(unit, not(is(emptyOrNullString())));
        assertThat(unit, matchesRegularExpression(noLeadingTrailingWhitespaceRegex));
    }

    @RepeatedTest(1000)
    public void testUnitOneThousand() {
        String unit = faker.starCraft().unit();
        // System.out.println(unit);
        assertThat(unit, not(is(emptyOrNullString())));
        assertThat(unit, matchesRegularExpression(noLeadingTrailingWhitespaceRegex));
    }

    @Test
    public void testBuilding() {
        String building = faker.starCraft().building();
        assertThat(building, not(is(emptyOrNullString())));
        assertThat(building, matchesRegularExpression(noLeadingTrailingWhitespaceRegex));
    }

    @Test
    public void testCharacter() {
        String character = faker.starCraft().character();
        assertThat(character, not(is(emptyOrNullString())));
        assertThat(character, matchesRegularExpression(noLeadingTrailingWhitespaceRegex));
    }

    @Test
    public void testPlanet() {
        String planet = faker.starCraft().planet();
        assertThat(planet, not(is(emptyOrNullString())));
        assertThat(planet, matchesRegularExpression(noLeadingTrailingWhitespaceRegex));
    }

    @RepeatedTest(1000)
    public void testPlanetOneThousand() {
        String planet = faker.starCraft().planet();
        // System.out.println(planet);
        assertThat(planet, not(is(emptyOrNullString())));
        assertThat(planet, matchesRegularExpression(noLeadingTrailingWhitespaceRegex));
    }

}
