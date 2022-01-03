package net.datafaker;

import org.junit.Test;

import static net.datafaker.matchers.IsStringWithContents.isStringWithContents;
import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class AviationTest extends AbstractFakerTest {

    @Test
    public void airport() {
        assertThat(faker.aviation().airport(), matchesRegularExpression("\\w{4}"));
    }

    @Test
    public void aircraft() {
        assertThat(faker.aviation().aircraft(), isStringWithContents());
    }

    @Test
    public void metar() {
        assertThat(faker.aviation().METAR(), isStringWithContents());
    }

    @Test
    public void flight_ICAO() {
        assertThat(faker.aviation().flight("ICAO"), matchesRegularExpression("[A-Z]{3}[0-9]+"));
    }

    @Test
    public void flight_IATA() {
        assertThat(faker.aviation().flight("IATA"), matchesRegularExpression("[A-Z]{2}[0-9]+"));
    }

    @Test
    public void flight_default() {
        assertThat(faker.aviation().flight(), matchesRegularExpression("[A-Z]{2}[0-9]+"));
    }

    @Test
    public void airline() {
        assertThat(faker.aviation().airline(), isStringWithContents());
    }
}
