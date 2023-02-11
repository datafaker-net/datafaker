package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

class AviationTest extends AbstractBasicProviderTest<BaseFaker> {

    @Test
    void airport() {
        assertThat(faker.aviation().airport()).matches("\\w{4}");
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

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.aviation().aircraft(), "aviation.aircraft"),
            TestSpec.of(() -> faker.aviation().METAR(), "aviation.metar"),
            TestSpec.of(() -> faker.aviation().airline(), "aviation.airline"));
    }
}
