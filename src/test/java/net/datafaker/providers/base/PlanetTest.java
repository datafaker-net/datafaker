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

class PlanetTest extends BaseFakerTest<BaseFaker> {

    private final Planet planet = faker.planet();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
                TestSpec.of(planet::name, "planet.name"));
    }

    @Test
    void shouldReturnCorrectJupiterMetadata() {
        String lengthOfDay = planet.lengthOfDay(JUPITER);
        assertEquals("0d 9h 56m", lengthOfDay);

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(JUPITER);
        assertEquals("778,412,010 km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(JUPITER);
        assertEquals("24.79 m/s^2", equatorialGravity);

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
        assertEquals("227,936,640 km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(MARS);
        assertEquals("3.71 m/s^2", equatorialGravity);

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
        assertEquals("108,208,930 km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(VENUS);
        assertEquals("8.87 m/s^2", equatorialGravity);

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
        assertEquals("2,870,972,200 km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(URANUS);
        assertEquals("8.87 m/s^2", equatorialGravity);

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
        assertEquals("57,909,175 km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(MERCURY);
        assertEquals("3.7 m/s^2", equatorialGravity);

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
        assertEquals("4,498,252,900 km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(NEPTUNE);
        assertEquals("11.15 m/s^2", equatorialGravity);

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
        assertEquals("149,597,890 km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(EARTH);
        assertEquals("9.8 m/s^2", equatorialGravity);

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
        assertEquals("1,426,725,400 km", meanDistanceFromTheSun);

        String equatorialGravity = planet.equatorialGravity(SATURN);
        assertEquals("10.44 m/s²", equatorialGravity);

        String mass = planet.mass(SATURN);
        assertEquals("5.683 × 10^26 kg", mass);

        String equatorialRadius = planet.equatorialRadius(SATURN);
        assertEquals("58,232 km", equatorialRadius);
    }
}
