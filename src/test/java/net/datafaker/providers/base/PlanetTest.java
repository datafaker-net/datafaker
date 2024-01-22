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
import static net.datafaker.providers.base.Planet.PlanetName.PLUTO;
import static org.assertj.core.api.Assertions.assertThat;

class PlanetTest extends BaseFakerTest<BaseFaker> {

    private final Planet planet = faker.planet();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
                TestSpec.of(planet::name, "planet.name"));
    }

    @Test
    void shouldHaveNinePlanets() {
        // PlanetName enum has 9 entries
        assertThat(Planet.PlanetName.values()).hasSize(9);
        // planet.yml has 9 names
        assertThat(getBaseList("planet.name")).hasSize(9);
    }

    @Test
    void shouldReturnCorrectJupiterMetadata() {
        String lengthOfDay = planet.lengthOfDay(JUPITER);
        assertThat(lengthOfDay).isEqualTo("0d 9h 56m");

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(JUPITER);
        assertThat(meanDistanceFromTheSun).isEqualTo("778,412,010 km");

        String equatorialGravity = planet.equatorialGravity(JUPITER);
        assertThat(equatorialGravity).isEqualTo("24.79 m/s^2");

        String mass = planet.mass(JUPITER);
        assertThat(mass).isEqualTo("1.8987×10^27 kg");

        String equatorialRadius = planet.equatorialRadius(JUPITER);
        assertThat(equatorialRadius).isEqualTo("71,492 km");
    }

    @Test
    void shouldReturnCorrectMarsMetadata() {
        String lengthOfDay = planet.lengthOfDay(MARS);
        assertThat(lengthOfDay).isEqualTo("1d 0h 37m");

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(MARS);
        assertThat(meanDistanceFromTheSun).isEqualTo("227,936,640 km");

        String equatorialGravity = planet.equatorialGravity(MARS);
        assertThat(equatorialGravity).isEqualTo("3.71 m/s^2");

        String mass = planet.mass(MARS);
        assertThat(mass).isEqualTo("6.4191×10^23 kg");

        String equatorialRadius = planet.equatorialRadius(MARS);
        assertThat(equatorialRadius).isEqualTo("3,396.19 km");
    }

    @Test
    void shouldReturnCorrectVenusMetadata() {
        String lengthOfDay = planet.lengthOfDay(VENUS);
        assertThat(lengthOfDay).isEqualTo("243d 0h 0m");

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(VENUS);
        assertThat(meanDistanceFromTheSun).isEqualTo("108,208,930 km");

        String equatorialGravity = planet.equatorialGravity(VENUS);
        assertThat(equatorialGravity).isEqualTo("8.87 m/s^2");

        String mass = planet.mass(VENUS);
        assertThat(mass).isEqualTo("4.8690×10^24 kg");

        String equatorialRadius = planet.equatorialRadius(VENUS);
        assertThat(equatorialRadius).isEqualTo("6,051.8 km");
    }

    @Test
    void shouldReturnCorrectUranusMetadata() {
        String lengthOfDay = planet.lengthOfDay(URANUS);
        assertThat(lengthOfDay).isEqualTo("0d 17h 14m");

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(URANUS);
        assertThat(meanDistanceFromTheSun).isEqualTo("2,870,972,200 km");

        String equatorialGravity = planet.equatorialGravity(URANUS);
        assertThat(equatorialGravity).isEqualTo("8.87 m/s^2");

        String mass = planet.mass(URANUS);
        assertThat(mass).isEqualTo("8.6849×10^25 kg");

        String equatorialRadius = planet.equatorialRadius(URANUS);
        assertThat(equatorialRadius).isEqualTo("25,559 km");
    }

    @Test
    void shouldReturnCorrectMercuryMetadata() {
        String lengthOfDay = planet.lengthOfDay(MERCURY);
        assertThat(lengthOfDay).isEqualTo("59d 0h 0m");

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(MERCURY);
        assertThat(meanDistanceFromTheSun).isEqualTo("57,909,175 km");

        String equatorialGravity = planet.equatorialGravity(MERCURY);
        assertThat(equatorialGravity).isEqualTo("3.70 m/s^2");

        String mass = planet.mass(MERCURY);
        assertThat(mass).isEqualTo("3.302×10^23 kg");

        String equatorialRadius = planet.equatorialRadius(MERCURY);
        assertThat(equatorialRadius).isEqualTo("2,440.53 km");
    }

    @Test
    void shouldReturnCorrectNeptuneMetadata() {
        String lengthOfDay = planet.lengthOfDay(NEPTUNE);
        assertThat(lengthOfDay).isEqualTo("0d 16h 6m");

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(NEPTUNE);
        assertThat(meanDistanceFromTheSun).isEqualTo("4,498,252,900 km");

        String equatorialGravity = planet.equatorialGravity(NEPTUNE);
        assertThat(equatorialGravity).isEqualTo("11.15 m/s^2");

        String mass = planet.mass(NEPTUNE);
        assertThat(mass).isEqualTo("1.0244×10^26 kg");

        String equatorialRadius = planet.equatorialRadius(NEPTUNE);
        assertThat(equatorialRadius).isEqualTo("24,764 km");
    }

    @Test
    void shouldReturnCorrectEarthMetadata() {
        String lengthOfDay = planet.lengthOfDay(EARTH);
        assertThat(lengthOfDay).isEqualTo("24h");

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(EARTH);
        assertThat(meanDistanceFromTheSun).isEqualTo("149,597,890 km");

        String equatorialGravity = planet.equatorialGravity(EARTH);
        assertThat(equatorialGravity).isEqualTo("9.8 m/s^2");

        String mass = planet.mass(EARTH);
        assertThat(mass).isEqualTo("5.972×10^24 kg");

        String equatorialRadius = planet.equatorialRadius(EARTH);
        assertThat(equatorialRadius).isEqualTo("6,378.1366 km");
    }

    @Test
    void shouldReturnCorrectSaturnMetadata() {
        String lengthOfDay = planet.lengthOfDay(SATURN);
        assertThat(lengthOfDay).isEqualTo("0d 10h 34m");

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(SATURN);
        assertThat(meanDistanceFromTheSun).isEqualTo("1,426,725,400 km");

        String equatorialGravity = planet.equatorialGravity(SATURN);
        assertThat(equatorialGravity).isEqualTo("10.44 m/s^2");

        String mass = planet.mass(SATURN);
        assertThat(mass).isEqualTo("5.6851×10^26 kg");

        String equatorialRadius = planet.equatorialRadius(SATURN);
        assertThat(equatorialRadius).isEqualTo("60,268 km");
    }

    @Test
    void shouldReturnCorrectPlutoMetadata() {
        String lengthOfDay = planet.lengthOfDay(PLUTO);
        assertThat(lengthOfDay).isEqualTo("6d 9h 0m");

        String meanDistanceFromTheSun = planet.meanDistanceFromTheSun(PLUTO);
        assertThat(meanDistanceFromTheSun).isEqualTo("5,906,380,000 km");

        String equatorialGravity = planet.equatorialGravity(PLUTO);
        assertThat(equatorialGravity).isEqualTo("0.620 m/s^2");

        String mass = planet.mass(PLUTO);
        assertThat(mass).isEqualTo("1.303x10^22 kg");

        String equatorialRadius = planet.equatorialRadius(PLUTO);
        assertThat(equatorialRadius).isEqualTo("1,188.3 km");
    }
}
