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
            TestSpec.of(() -> planet.meanDistanceFromTheSun(JUPITER), "planet.distance_from_sun.jupiter"),
            TestSpec.of(() -> planet.equatorialGravity(JUPITER), "planet.equatorialGravity.jupiter"),
            TestSpec.of(() -> planet.mass(JUPITER), "planet.mass.jupiter"),
            TestSpec.of(() -> planet.equatorialRadius(JUPITER), "planet.equatorialRadius.jupiter"),

            TestSpec.of(() -> planet.lengthOfDay(MARS), "planet.length_of_day.mars"),
            TestSpec.of(() -> planet.meanDistanceFromTheSun(MARS), "planet.distance_from_sun.mars"),
            TestSpec.of(() -> planet.equatorialGravity(MARS), "planet.equatorialGravity.mars"),
            TestSpec.of(() -> planet.mass(MARS), "planet.mass.mars"),
            TestSpec.of(() -> planet.equatorialRadius(MARS), "planet.equatorialRadius.mars"),

            TestSpec.of(() -> planet.lengthOfDay(VENUS), "planet.length_of_day.venus"),
            TestSpec.of(() -> planet.meanDistanceFromTheSun(VENUS), "planet.distance_from_sun.venus"),
            TestSpec.of(() -> planet.equatorialGravity(VENUS), "planet.equatorialGravity.venus"),
            TestSpec.of(() -> planet.mass(VENUS), "planet.mass.venus"),
            TestSpec.of(() -> planet.equatorialRadius(VENUS), "planet.equatorialRadius.venus"),

            TestSpec.of(() -> planet.lengthOfDay(URANUS), "planet.length_of_day.uranus"),
            TestSpec.of(() -> planet.meanDistanceFromTheSun(URANUS), "planet.distance_from_sun.uranus"),
            TestSpec.of(() -> planet.equatorialGravity(URANUS), "planet.equatorialGravity.uranus"),
            TestSpec.of(() -> planet.mass(URANUS), "planet.mass.uranus"),
            TestSpec.of(() -> planet.equatorialRadius(URANUS), "planet.equatorialRadius.uranus"),

            TestSpec.of(() -> planet.lengthOfDay(MERCURY), "planet.length_of_day.mercury"),
            TestSpec.of(() -> planet.meanDistanceFromTheSun(MERCURY), "planet.distance_from_sun.mercury"),
            TestSpec.of(() -> planet.equatorialGravity(MERCURY), "planet.equatorialGravity.mercury"),
            TestSpec.of(() -> planet.mass(MERCURY), "planet.mass.mercury"),
            TestSpec.of(() -> planet.equatorialRadius(MERCURY), "planet.equatorialRadius.mercury"),

            TestSpec.of(() -> planet.lengthOfDay(NEPTUNE), "planet.length_of_day.neptune"),
            TestSpec.of(() -> planet.meanDistanceFromTheSun(NEPTUNE), "planet.distance_from_sun.neptune"),
            TestSpec.of(() -> planet.equatorialGravity(NEPTUNE), "planet.equatorialGravity.neptune"),
            TestSpec.of(() -> planet.mass(NEPTUNE), "planet.mass.neptune"),
            TestSpec.of(() -> planet.equatorialRadius(NEPTUNE), "planet.equatorialRadius.neptune"),

            TestSpec.of(() -> planet.lengthOfDay(EARTH), "planet.length_of_day.earth"),
            TestSpec.of(() -> planet.meanDistanceFromTheSun(EARTH), "planet.distance_from_sun.earth"),
            TestSpec.of(() -> planet.equatorialGravity(EARTH), "planet.equatorialGravity.earth"),
            TestSpec.of(() -> planet.mass(EARTH), "planet.mass.earth"),
            TestSpec.of(() -> planet.equatorialRadius(EARTH), "planet.equatorialRadius.earth"),

            TestSpec.of(() -> planet.lengthOfDay(SATURN), "planet.length_of_day.saturn"),
            TestSpec.of(() -> planet.meanDistanceFromTheSun(SATURN), "planet.distance_from_sun.saturn"),
            TestSpec.of(() -> planet.equatorialGravity(SATURN), "planet.equatorialGravity.saturn"),
            TestSpec.of(() -> planet.mass(SATURN), "planet.mass.saturn"),
            TestSpec.of(() -> planet.equatorialRadius(SATURN), "planet.equatorialRadius.saturn")
        );
    }

    @Test
    void shouldReturnCorrectJupiterMetadata() {
        String lengthOfDay = planet.lengthOfDay(JUPITER);
        assertEquals("0d 9h 56m", lengthOfDay);

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(JUPITER);
        assertEquals("778.5 million km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(JUPITER);
        assertEquals("24.79 m/s²", equatorialGravity);

        String mass = planet.mass(JUPITER);
        assertEquals("1.898 × 10^27 kg (317.8 M⊕)", mass);

        String equatorialRadius = planet.equatorialRadius(JUPITER);
        assertEquals("69,911 km", equatorialRadius);
    }

    @Test
    void shouldReturnCorrectMarsMetadata() {
        String lengthOfDay = planet.lengthOfDay(MARS);
        assertEquals("1d 0h 37m", lengthOfDay);

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(MARS);
        assertEquals("227.9 million km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(MARS);
        assertEquals("3.71 m/s²", equatorialGravity);

        String mass = planet.mass(MARS);
        assertEquals("6.39 × 10^23 kg", mass);

        String equatorialRadius = planet.equatorialRadius(MARS);
        assertEquals("3,389.5 km", equatorialRadius);
    }

    @Test
    void shouldReturnCorrectVenusMetadata() {
        String lengthOfDay = planet.lengthOfDay(VENUS);
        assertEquals("243d 0h 0m", lengthOfDay);

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(VENUS);
        assertEquals("108.2 million km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(VENUS);
        assertEquals("8.87 m/s²", equatorialGravity);

        String mass = planet.mass(VENUS);
        assertEquals("4.867 × 10^24 kg (0.815 M⊕)", mass);

        String equatorialRadius = planet.equatorialRadius(VENUS);
        assertEquals("6,051.8 km", equatorialRadius);
    }

    @Test
    void shouldReturnCorrectUranusMetadata() {
        String lengthOfDay = planet.lengthOfDay(URANUS);
        assertEquals("0d 17h 14m", lengthOfDay);

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(URANUS);
        assertEquals("2.871 billion km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(URANUS);
        assertEquals("8.87 m/s²", equatorialGravity);

        String mass = planet.mass(URANUS);
        assertEquals("8.681 × 10^25 kg", mass);

        String equatorialRadius = planet.equatorialRadius(URANUS);
        assertEquals("25,362 km", equatorialRadius);
    }

    @Test
    void shouldReturnCorrectMercuryMetadata() {
        String lengthOfDay = planet.lengthOfDay(MERCURY);
        assertEquals("59d 0h 0m", lengthOfDay);

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(MERCURY);
        assertEquals("58 million km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(MERCURY);
        assertEquals("3.7 m/s²", equatorialGravity);

        String mass = planet.mass(MERCURY);
        assertEquals("3.285 × 10^23 kg (0.055 M⊕)", mass);

        String equatorialRadius = planet.equatorialRadius(MERCURY);
        assertEquals("2,439.7 km", equatorialRadius);
    }

    @Test
    void shouldReturnCorrectNeptuneMetadata() {
        String lengthOfDay = planet.lengthOfDay(NEPTUNE);
        assertEquals("0d 16h 6m", lengthOfDay);

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(NEPTUNE);
        assertEquals("4.495 billion km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(NEPTUNE);
        assertEquals("11.15 m/s²", equatorialGravity);

        String mass = planet.mass(NEPTUNE);
        assertEquals("1.024 × 10^26 kg", mass);

        String equatorialRadius = planet.equatorialRadius(NEPTUNE);
        assertEquals("24,622 km", equatorialRadius);
    }

    @Test
    void shouldReturnCorrectEarthMetadata() {
        String lengthOfDay = planet.lengthOfDay(EARTH);
        assertEquals("24h", lengthOfDay);

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(EARTH);
        assertEquals("147.1 million km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(EARTH);
        assertEquals("9.80665 m/s2", equatorialGravity);

        String mass = planet.mass(EARTH);
        assertEquals("5.972168×1024 kg", mass);

        String equatorialRadius = planet.equatorialRadius(EARTH);
        assertEquals("6371.0 km", equatorialRadius);
    }

    @Test
    void shouldReturnCorrectSaturnMetadata() {
        String lengthOfDay = planet.lengthOfDay(SATURN);
        assertEquals("0d 10h 34m", lengthOfDay);

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(SATURN);
        assertEquals("1.434 billion km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(SATURN);
        assertEquals("10.44 m/s²", equatorialGravity);

        String mass = planet.mass(SATURN);
        assertEquals("5.683 × 10^26 kg", mass);

        String equatorialRadius = planet.equatorialRadius(SATURN);
        assertEquals("58,232 km", equatorialRadius);
    }
}
