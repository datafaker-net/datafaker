package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class WeatherTest extends BaseFakerTest<BaseFaker> {

    private final Weather weather = faker.weather();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(weather::description, "weather.description"));
    }

    @Test
    void temperatureCelsius() {
        assertThat(weather.temperatureCelsius()).matches("-?\\d+°C");
    }

    @Test
    void temperatureFahrenheit() {
        assertThat(weather.temperatureFahrenheit()).matches("-?\\d+°F");
    }

    @Test
    void temperatureCelsiusInRange() {
        for (int i = 1; i < 100; i++) {
            assertThat(weather.temperatureCelsius(-5, 5)).matches("-?[0-5]°C");
        }
    }

    @Test
    void temperatureFahrenheitInRange() {
        for (int i = 1; i < 100; i++) {
            assertThat(weather.temperatureFahrenheit(-5, 5)).matches("-?[0-5]°F");
        }
    }
}
