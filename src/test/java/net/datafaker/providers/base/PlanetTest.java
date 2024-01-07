package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static net.datafaker.providers.base.Planet.PlanetName.EARTH;
import static net.datafaker.providers.base.Planet.PlanetName.JUPITER;
import static net.datafaker.providers.base.Planet.PlanetName.MARS;
import static net.datafaker.providers.base.Planet.PlanetName.MERCURY;
import static net.datafaker.providers.base.Planet.PlanetName.NEPTUNE;
import static net.datafaker.providers.base.Planet.PlanetName.SATURN;
import static net.datafaker.providers.base.Planet.PlanetName.URANUS;
import static net.datafaker.providers.base.Planet.PlanetName.VENUS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanetTest extends BaseFakerTest<BaseFaker> {

    private final Planet planet = faker.planet();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(planet::name, "planet.name"),
            TestSpec.of(() -> planet.lengthOfDay(JUPITER), "planet.length_of_day.jupiter"),
            TestSpec.of(() -> planet.distanceFromSun(JUPITER), "planet.distance_from_sun.jupiter"),
            TestSpec.of(() -> planet.gravity(JUPITER), "planet.gravity.jupiter"),
            TestSpec.of(() -> planet.mass(JUPITER), "planet.mass.jupiter"),
            TestSpec.of(() -> planet.radius(JUPITER), "planet.radius.jupiter"),

            TestSpec.of(() -> planet.lengthOfDay(MARS), "planet.length_of_day.mars"),
            TestSpec.of(() -> planet.distanceFromSun(MARS), "planet.distance_from_sun.mars"),
            TestSpec.of(() -> planet.gravity(MARS), "planet.gravity.mars"),
            TestSpec.of(() -> planet.mass(MARS), "planet.mass.mars"),
            TestSpec.of(() -> planet.radius(MARS), "planet.radius.mars"),

            TestSpec.of(() -> planet.lengthOfDay(VENUS), "planet.length_of_day.venus"),
            TestSpec.of(() -> planet.distanceFromSun(VENUS), "planet.distance_from_sun.venus"),
            TestSpec.of(() -> planet.gravity(VENUS), "planet.gravity.venus"),
            TestSpec.of(() -> planet.mass(VENUS), "planet.mass.venus"),
            TestSpec.of(() -> planet.radius(VENUS), "planet.radius.venus"),

            TestSpec.of(() -> planet.lengthOfDay(URANUS), "planet.length_of_day.uranus"),
            TestSpec.of(() -> planet.distanceFromSun(URANUS), "planet.distance_from_sun.uranus"),
            TestSpec.of(() -> planet.gravity(URANUS), "planet.gravity.uranus"),
            TestSpec.of(() -> planet.mass(URANUS), "planet.mass.uranus"),
            TestSpec.of(() -> planet.radius(URANUS), "planet.radius.uranus"),

            TestSpec.of(() -> planet.lengthOfDay(MERCURY), "planet.length_of_day.mercury"),
            TestSpec.of(() -> planet.distanceFromSun(MERCURY), "planet.distance_from_sun.mercury"),
            TestSpec.of(() -> planet.gravity(MERCURY), "planet.gravity.mercury"),
            TestSpec.of(() -> planet.mass(MERCURY), "planet.mass.mercury"),
            TestSpec.of(() -> planet.radius(MERCURY), "planet.radius.mercury"),

            TestSpec.of(() -> planet.lengthOfDay(NEPTUNE), "planet.length_of_day.neptune"),
            TestSpec.of(() -> planet.distanceFromSun(NEPTUNE), "planet.distance_from_sun.neptune"),
            TestSpec.of(() -> planet.gravity(NEPTUNE), "planet.gravity.neptune"),
            TestSpec.of(() -> planet.mass(NEPTUNE), "planet.mass.neptune"),
            TestSpec.of(() -> planet.radius(NEPTUNE), "planet.radius.neptune"),

            TestSpec.of(() -> planet.lengthOfDay(EARTH), "planet.length_of_day.earth"),
            TestSpec.of(() -> planet.distanceFromSun(EARTH), "planet.distance_from_sun.earth"),
            TestSpec.of(() -> planet.gravity(EARTH), "planet.gravity.earth"),
            TestSpec.of(() -> planet.mass(EARTH), "planet.mass.earth"),
            TestSpec.of(() -> planet.radius(EARTH), "planet.radius.earth"),

            TestSpec.of(() -> planet.lengthOfDay(SATURN), "planet.length_of_day.saturn"),
            TestSpec.of(() -> planet.distanceFromSun(SATURN), "planet.distance_from_sun.saturn"),
            TestSpec.of(() -> planet.gravity(SATURN), "planet.gravity.saturn"),
            TestSpec.of(() -> planet.mass(SATURN), "planet.mass.saturn"),
            TestSpec.of(() -> planet.radius(SATURN), "planet.radius.saturn")
        );
    }

    @Test
    void shouldReturnCorrectJupiterMetadata() {
        String lengthOfDay = planet.lengthOfDay(JUPITER);
        assertEquals("0d 9h 56m", lengthOfDay);

        String distanceFromSun = planet.distanceFromSun(JUPITER);
        assertEquals("778.5 million km", distanceFromSun);

        String gravity = planet.gravity(JUPITER);
        assertEquals("24.79 m/s²", gravity);

        String mass = planet.mass(JUPITER);
        assertEquals("1.898 × 10^27 kg (317.8 M⊕)", mass);

        String radius = planet.radius(JUPITER);
        assertEquals("69,911 km", radius);
    }

    @Test
    void shouldReturnCorrectMarsMetadata() {
        String lengthOfDay = planet.lengthOfDay(MARS);
        assertEquals("1d 0h 37m", lengthOfDay);

        String distanceFromSun = planet.distanceFromSun(MARS);
        assertEquals("227.9 million km", distanceFromSun);

        String gravity = planet.gravity(MARS);
        assertEquals("3.71 m/s²", gravity);

        String mass = planet.mass(MARS);
        assertEquals("6.39 × 10^23 kg", mass);

        String radius = planet.radius(MARS);
        assertEquals("3,389.5 km", radius);
    }

    @Test
    void shouldReturnCorrectVenusMetadata() {
        String lengthOfDay = planet.lengthOfDay(VENUS);
        assertEquals("243d 0h 0m", lengthOfDay);

        String distanceFromSun = planet.distanceFromSun(VENUS);
        assertEquals("108.2 million km", distanceFromSun);

        String gravity = planet.gravity(VENUS);
        assertEquals("8.87 m/s²", gravity);

        String mass = planet.mass(VENUS);
        assertEquals("4.867 × 10^24 kg (0.815 M⊕)", mass);

        String radius = planet.radius(VENUS);
        assertEquals("6,051.8 km", radius);
    }

    @Test
    void shouldReturnCorrectUranusMetadata() {
        String lengthOfDay = planet.lengthOfDay(URANUS);
        assertEquals("0d 17h 14m", lengthOfDay);

        String distanceFromSun = planet.distanceFromSun(URANUS);
        assertEquals("2.871 billion km", distanceFromSun);

        String gravity = planet.gravity(URANUS);
        assertEquals("8.87 m/s²", gravity);

        String mass = planet.mass(URANUS);
        assertEquals("8.681 × 10^25 kg", mass);

        String radius = planet.radius(URANUS);
        assertEquals("25,362 km", radius);
    }

    @Test
    void shouldReturnCorrectMercuryMetadata() {
        String lengthOfDay = planet.lengthOfDay(MERCURY);
        assertEquals("59d 0h 0m", lengthOfDay);

        String distanceFromSun = planet.distanceFromSun(MERCURY);
        assertEquals("58 million km", distanceFromSun);

        String gravity = planet.gravity(MERCURY);
        assertEquals("3.7 m/s²", gravity);

        String mass = planet.mass(MERCURY);
        assertEquals("3.285 × 10^23 kg (0.055 M⊕)", mass);

        String radius = planet.radius(MERCURY);
        assertEquals("2,439.7 km", radius);
    }

    @Test
    void shouldReturnCorrectNeptuneMetadata() {
        String lengthOfDay = planet.lengthOfDay(NEPTUNE);
        assertEquals("0d 16h 6m", lengthOfDay);

        String distanceFromSun = planet.distanceFromSun(NEPTUNE);
        assertEquals("4.495 billion km", distanceFromSun);

        String gravity = planet.gravity(NEPTUNE);
        assertEquals("11.15 m/s²", gravity);

        String mass = planet.mass(NEPTUNE);
        assertEquals("1.024 × 10^26 kg", mass);

        String radius = planet.radius(NEPTUNE);
        assertEquals("24,622 km", radius);
    }

    @Test
    void shouldReturnCorrectEarthMetadata() {
        String lengthOfDay = planet.lengthOfDay(EARTH);
        assertEquals("24h", lengthOfDay);

        String distanceFromSun = planet.distanceFromSun(EARTH);
        assertEquals("147.1 million km", distanceFromSun);

        String gravity = planet.gravity(EARTH);
        assertEquals("9.80665 m/s2", gravity);

        String mass = planet.mass(EARTH);
        assertEquals("5.972168×1024 kg", mass);

        String radius = planet.radius(EARTH);
        assertEquals("6371.0 km", radius);
    }

    @Test
    void shouldReturnCorrectSaturnMetadata() {
        String lengthOfDay = planet.lengthOfDay(SATURN);
        assertEquals("0d 10h 34m", lengthOfDay);

        String distanceFromSun = planet.distanceFromSun(SATURN);
        assertEquals("1.434 billion km", distanceFromSun);

        String gravity = planet.gravity(SATURN);
        assertEquals("10.44 m/s²", gravity);

        String mass = planet.mass(SATURN);
        assertEquals("5.683 × 10^26 kg", mass);

        String radius = planet.radius(SATURN);
        assertEquals("58,232 km", radius);
    }
}
