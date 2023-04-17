package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class AviationTest extends BaseFakerTest<BaseFaker> {

    private final Aviation aviation = faker.aviation();

    @Test
    void flight_ICAO() {
        assertThat(aviation.flight("ICAO")).matches("[A-Z]{3}[0-9]+");
    }

    @Test
    void flight_IATA() {
        assertThat(aviation.flight("IATA")).matches("[A-Z0-9]{2}\\d{1,4}");
    }

    @Test
    void flight_default() {
        assertThat(aviation.flight()).matches("[A-Z0-9]{2}\\d{1,4}");
    }

    @Test
    void aircraft() {
        assertThat(aviation.aircraft()).isNotEmpty();
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(aviation::airport, "aviation.airport", "\\w{4}"),
            TestSpec.of(aviation::airportName, "aviation.airport_name"),
            TestSpec.of(aviation::airplane, "aviation.aircraft.airplane"),
            TestSpec.of(aviation::warplane, "aviation.aircraft.warplane"),
            TestSpec.of(aviation::general, "aviation.aircraft.general"),
            TestSpec.of(aviation::cargo, "aviation.aircraft.cargo"),
            TestSpec.of(aviation::civilHelicopter, "aviation.aircraft.civil_helicopter"),
            TestSpec.of(aviation::armyHelicopter, "aviation.aircraft.army_helicopter"),
            TestSpec.of(aviation::METAR, "aviation.metar"),
            TestSpec.of(aviation::airline, "aviation.airline"));
    }
}
