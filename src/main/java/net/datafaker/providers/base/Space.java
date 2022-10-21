package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Space extends AbstractProvider<BaseProviders> {

    protected Space(BaseProviders faker) {
        super(faker);
    }

    public String planet() {
        return resolve("space.planet");
    }

    public String moon() {
        return resolve("space.moon");
    }

    public String galaxy() {
        return resolve("space.galaxy");
    }

    public String nebula() {
        return resolve("space.nebula");
    }

    public String starCluster() {
        return resolve("space.star_cluster");
    }

    public String constellation() {
        return resolve("space.constellation");
    }

    public String star() {
        return resolve("space.star");
    }

    public String agency() {
        return resolve("space.agency");
    }

    public String agencyAbbreviation() {
        return resolve("space.agency_abv");
    }

    public String nasaSpaceCraft() {
        return resolve("space.nasa_space_craft");
    }

    public String company() {
        return resolve("space.company");
    }

    public String distanceMeasurement() {
        return faker.number().numberBetween(10, 100) + ' ' + resolve("space.distance_measurement");
    }

    public String meteorite() {
        return resolve("space.meteorite");
    }
}
