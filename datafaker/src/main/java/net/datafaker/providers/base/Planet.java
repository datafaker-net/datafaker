package net.datafaker.providers.base;

/**
 * Provides planet specific metadata like length of the day, radius, mass etc.
 *
 * @since 2.2.0
 */
public class Planet extends AbstractProvider<BaseProviders> {

    protected Planet(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("planet.name");
    }

    public String symbol() {
        return symbol(faker.options().option(PlanetName.class));
    }

    public String symbol(PlanetName planetName) {
        return resolve("planet.symbol." + planetName.getName());
    }

    public String classification() {
        return classification(faker.options().option(PlanetName.class));
    }

    public String classification(PlanetName planetName) {
        return resolve("planet.classification." + planetName.getName());
    }

    public String discoveryYear() {
        return discoveryYear(faker.options().option(PlanetName.class));
    }

    public String discoveryYear(PlanetName planetName) {
        return resolve("planet.discovery_year." + planetName.getName());
    }

    public String meanDistanceFromTheSun() {
        return meanDistanceFromTheSun(faker.options().option(PlanetName.class));
    }

    public String meanDistanceFromTheSun(PlanetName planetName) {
        return resolve("planet.mean_distance_from_the_sun." + planetName.getName());
    }

    public String lengthOfDay() {
        return meanDistanceFromTheSun(faker.options().option(PlanetName.class));
    }

    public String lengthOfDay(PlanetName planetName) {
        return resolve("planet.length_of_day." + planetName.getName());
    }

    public String equatorialRadius() {
        return equatorialRadius(faker.options().option(PlanetName.class));
    }

    public String equatorialRadius(PlanetName planetName) {
        return resolve("planet.equatorial_radius." + planetName.getName());
    }

    public String surfaceArea() {
        return surfaceArea(faker.options().option(PlanetName.class));
    }

    public String surfaceArea(PlanetName planetName) {
        return resolve("planet.surface_area." + planetName.getName());
    }

    public String volume() {
        return volume(faker.options().option(PlanetName.class));
    }

    public String volume(PlanetName planetName) {
        return resolve("planet.volume." + planetName.getName());
    }

    public String mass() {
        return mass(faker.options().option(PlanetName.class));
    }

    public String mass(PlanetName planetName) {
        return resolve("planet.mass." + planetName.getName());
    }

    public String gravitationParameters() {
        return gravitationParameters(faker.options().option(PlanetName.class));
    }

    public String gravitationParameters(PlanetName planetName) {
        return resolve("planet.gravitational_parameters." + planetName.getName());
    }

    public String density() {
        return density(faker.options().option(PlanetName.class));
    }

    public String density(PlanetName planetName) {
        return resolve("planet.density." + planetName.getName());
    }

    public String equatorialGravity() {
        return equatorialGravity(faker.options().option(PlanetName.class));
    }

    public String equatorialGravity(PlanetName planetName) {
        return resolve("planet.equatorial_gravity." + planetName.getName());
    }

    public String escapeVelocity() {
        return escapeVelocity(faker.options().option(PlanetName.class));
    }

    public String escapeVelocity(PlanetName planetName) {
        return resolve("planet.escape_velocity." + planetName.getName());
    }

    public String rotationPeriod() {
        return rotationPeriod(faker.options().option(PlanetName.class));
    }

    public String rotationPeriod(PlanetName planetName) {
        return resolve("planet.rotation_period." + planetName.getName());
    }

    public String orbitalPeriod() {
        return orbitalPeriod(faker.options().option(PlanetName.class));
    }

    public String orbitalPeriod(PlanetName planetName) {
        return resolve("planet.orbital_period." + planetName.getName());
    }

    public String meanOrbitalSpeed() {
        return meanOrbitalSpeed(faker.options().option(PlanetName.class));
    }

    public String meanOrbitalSpeed(PlanetName planetName) {
        return resolve("planet.mean_orbital_speed." + planetName.getName());
    }

    public String eccentricity() {
        return eccentricity(faker.options().option(PlanetName.class));
    }

    public String eccentricity(PlanetName planetName) {
        return resolve("planet.eccentricity." + planetName.getName());
    }

    public String inclination() {
        return inclination(faker.options().option(PlanetName.class));
    }

    public String inclination(PlanetName planetName) {
        return resolve("planet.inclination." + planetName.getName());
    }

    public String axialTilt() {
        return axialTilt(faker.options().option(PlanetName.class));
    }

    public String axialTilt(PlanetName planetName) {
        return resolve("planet.axial_tilt." + planetName.getName());
    }

    public String meanSurfaceTemperature() {
        return meanSurfaceTemperature(faker.options().option(PlanetName.class));
    }

    public String meanSurfaceTemperature(PlanetName planetName) {
        return resolve("planet.mean_surface_temperature." + planetName.getName());
    }

    public String meanAirTemperature() {
        return meanAirTemperature(faker.options().option(PlanetName.class));
    }

    public String meanAirTemperature(PlanetName planetName) {
        return resolve("planet.mean_air_temperature." + planetName.getName());
    }

    public String atmosphericComposition() {
        return atmosphericComposition(faker.options().option(PlanetName.class));
    }

    public String atmosphericComposition(PlanetName planetName) {
        return resolve("planet.atmospheric_composition." + planetName.getName());
    }

    public String numberOfKnownMoons() {
        return numberOfKnownMoons(faker.options().option(PlanetName.class));
    }

    public String numberOfKnownMoons(PlanetName planetName) {
        return resolve("planet.number_of_known_moons." + planetName.getName());
    }

    public String rings() {
        return rings(faker.options().option(PlanetName.class));
    }

    public String rings(PlanetName planetName) {
        return resolve("planet.rings." + planetName.getName());
    }

    public String planetaryDiscriminant() {
        return planetaryDiscriminant(faker.options().option(PlanetName.class));
    }

    public String planetaryDiscriminant(PlanetName planetName) {
        return resolve("planet.planetary_discriminant." + planetName.getName());
    }

    public enum PlanetName {
        JUPITER("jupiter"),
        MARS("mars"),
        VENUS("venus"),
        URANUS("uranus"),
        MERCURY("mercury"),
        NEPTUNE("neptune"),
        EARTH("earth"),
        SATURN("saturn"),
        PLUTO("pluto");

        private final String name;

        PlanetName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
