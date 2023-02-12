package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class AviationTest extends BaseFakerTest<BaseFaker> {

    private Aviation aviation = faker.aviation();

    @Test
    void flight_ICAO() {
        assertThat(aviation.flight("ICAO")).matches("[A-Z]{3}[0-9]+");
    }

    @Test
    void flight_IATA() {
        assertThat(aviation.flight("IATA")).matches("[A-Z]{2}[0-9]+");
    }

    @Test
    void flight_default() {
        assertThat(aviation.flight()).matches("[A-Z]{2}[0-9]+");
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(aviation::airport, "aviation.airport", "\\w{4}"),
            TestSpec.of(aviation::aircraft, "aviation.aircraft"),
            TestSpec.of(aviation::METAR, "aviation.metar"),
            TestSpec.of(aviation::airline, "aviation.airline"));
    }
}
