package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherTest extends AbstractFakerTest {

    @Test
    void description() {
        assertThat(faker.weather().description()).isNotEmpty();
    }

    @Test
    void temperatureCelsius() {
        assertThat(faker.weather().temperatureCelsius()).matches("-?\\d+°C");
    }

    @Test
    void temperatureFahrenheit() {
        assertThat(faker.weather().temperatureFahrenheit()).matches("-?\\d+°F");
    }

    @Test
    void temperatureCelsiusInRange() {
        for (int i = 1; i < 100; i++) {
            assertThat(faker.weather().temperatureCelsius(-5, 5)).matches("-?[0-5]°C");
        }
    }

    @Test
    void temperatureFahrenheitInRange() {
        for (int i = 1; i < 100; i++) {
            assertThat(faker.weather().temperatureFahrenheit(-5, 5)).matches("-?[0-5]°F");
        }
    }
}
