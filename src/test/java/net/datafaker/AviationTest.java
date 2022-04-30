package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AviationTest extends AbstractFakerTest {

    @Test
    void airport() {
        assertThat(faker.aviation().airport()).matches("\\w{4}");
    }

    @Test
    void aircraft() {
        assertThat(faker.aviation().aircraft()).isNotEmpty();
    }

    @Test
    void metar() {
        assertThat(faker.aviation().METAR()).isNotEmpty();
    }

    @Test
    void flight_ICAO() {
        assertThat(faker.aviation().flight("ICAO")).matches("[A-Z]{3}[0-9]+");
    }

    @Test
    void flight_IATA() {
        assertThat(faker.aviation().flight("IATA")).matches("[A-Z]{2}[0-9]+");
    }

    @Test
    void flight_default() {
        assertThat(faker.aviation().flight()).matches("[A-Z]{2}[0-9]+");
    }

    @Test
    void airline() {
        assertThat(faker.aviation().airline()).isNotEmpty();
    }
}
