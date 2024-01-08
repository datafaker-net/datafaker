package net.datafaker.providers.base;

/**
 * Provides planet specific metadata like length of the day, radius, mass etc.
 *
 * @since 2.1.1
 */
public class Planet extends AbstractProvider<BaseProviders> {

    protected Planet(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("planet.name");
    }

    public String lengthOfDay() {
        return lengthOfDay(faker.options().option(PlanetName.class));
    }

    public String lengthOfDay(PlanetName planetName) {
        return resolve("planet.length_of_day." + planetName.getName());
    }

    public String mass() {
        return mass(faker.options().option(PlanetName.class));
    }

    public String mass(PlanetName planetName) {
        return resolve("planet.mass." + planetName.getName());
    }

    public String gravity() {
        return gravity(faker.options().option(PlanetName.class));
    }

    public String gravity(PlanetName planetName) {
        return resolve("planet.gravity." + planetName.getName());
    }

    public String radius() {
        return radius(faker.options().option(PlanetName.class));
    }

    public String radius(PlanetName planetName) {
        return resolve("planet.radius." + planetName.getName());
    }

    public String distanceFromSun() {
        return distanceFromSun(faker.options().option(PlanetName.class));
    }

    public String distanceFromSun(PlanetName planetName) {
        return resolve("planet.distance_from_sun." + planetName.getName());
    }

    public enum PlanetName {
        JUPITER("jupiter"),
        MARS("mars"),
        VENUS("venus"),
        URANUS("uranus"),
        MERCURY("mercury"),
        NEPTUNE("neptune"),
        EARTH("earth"),
        SATURN("saturn");

        private final String name;

        PlanetName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
