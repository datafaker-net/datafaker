package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WeatherTest extends AbstractFakerTest {

    @Test
    public void description() {
        assertThat(faker.weather().description()).isNotEmpty();
    }

    @Test
    public void temperatureCelsius() {
        assertThat(faker.weather().temperatureCelsius()).matches("[-]?\\d+°C");
    }

    @Test
    public void temperatureFahrenheit() {
        assertThat(faker.weather().temperatureFahrenheit()).matches("[-]?\\d+°F");
    }

    @Test
    public void temperatureCelsiusInRange() {
        for (int i = 1; i < 100; i++) {
            assertThat(faker.weather().temperatureCelsius(-5, 5)).matches("[-]?[0-5]°C");
        }
    }

    @Test
    public void temperatureFahrenheitInRange() {
        for (int i = 1; i < 100; i++) {
            assertThat(faker.weather().temperatureFahrenheit(-5, 5)).matches("[-]?[0-5]°F");
        }
    }
}
