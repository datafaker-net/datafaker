package net.datafaker.providers.science;

import net.datafaker.providers.base.AbstractProvider;

import java.util.Locale;

/**
 * Provides planet specific metadata like length of the day, radius, mass etc.
 *
 * @since 2.1.1
 */
public class Planet extends AbstractProvider<ScienceProviders> {

    protected Planet(ScienceProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("planet.name");
    }

    public String lengthOfDay() {
        return lengthOfDay(faker.options().option(PlanetName.class));
    }

    public String lengthOfDay(PlanetName planetName) {
        return resolve("planet.length_of_day." + lowerCaseName(planetName));
    }

    public String mass() {
        return mass(faker.options().option(PlanetName.class));
    }

    public String mass(PlanetName planetName) {
        return resolve("planet.mass." + lowerCaseName(planetName));
    }

    public String gravity() {
        return gravity(faker.options().option(PlanetName.class));
    }

    public String gravity(PlanetName planetName) {
        return resolve("planet.gravity." + lowerCaseName(planetName));
    }

    public String radius() {
        return radius(faker.options().option(PlanetName.class));
    }

    public String radius(PlanetName planetName) {
        return resolve("planet.radius." + lowerCaseName(planetName));
    }

    public String distanceFromSun() {
        return distanceFromSun(faker.options().option(PlanetName.class));
    }

    public String distanceFromSun(PlanetName planetName) {
        return resolve("planet.distance_from_sun." + lowerCaseName(planetName));
    }

    private String lowerCaseName(PlanetName planetName) {
        return planetName.toString().toLowerCase(Locale.ROOT);
    }

    public enum PlanetName {
        JUPITER,
        MARS,
        VENUS,
        URANUS,
        MERCURY,
        NEPTUNE,
        EARTH,
        SATURN,
        PLUTO
    }

}
