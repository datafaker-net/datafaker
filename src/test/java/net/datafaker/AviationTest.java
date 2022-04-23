package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AviationTest extends AbstractFakerTest {

    @Test
    public void airport() {
        assertThat(faker.aviation().airport()).matches("\\w{4}");
    }

    @Test
    public void aircraft() {
        assertThat(faker.aviation().aircraft()).isNotEmpty();
    }

    @Test
    public void metar() {
        assertThat(faker.aviation().METAR()).isNotEmpty();
    }

    @Test
    public void flight_ICAO() {
        assertThat(faker.aviation().flight("ICAO")).matches("[A-Z]{3}[0-9]+");
    }

    @Test
    public void flight_IATA() {
        assertThat(faker.aviation().flight("IATA")).matches("[A-Z]{2}[0-9]+");
    }

    @Test
    public void flight_default() {
        assertThat(faker.aviation().flight()).matches("[A-Z]{2}[0-9]+");
    }

    @Test
    public void airline() {
        assertThat(faker.aviation().airline()).isNotEmpty();
    }
}
