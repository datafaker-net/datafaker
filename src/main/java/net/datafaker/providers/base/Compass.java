package net.datafaker.providers.base;

/**
 * @since 1.7.0
 */
public class Compass extends AbstractProvider<BaseProviders> {

    protected Compass(BaseProviders faker) {
        super(faker);
    }

    public String azimuth() {
        return resolve("compass.azimuth");
    }

    public String directionWord() {
        return resolve("compass.direction");
    }

    public String directionAbbreviation() {
        return resolve("compass.abbreviation");
    }

    public String cardinalDirectionWord() {
        return resolve("compass.cardinal.word");
    }

    public String cardinalDirectionAbbreviation() {
        return resolve("compass.cardinal.abbreviation");
    }

    public String cardinalDirectionAzimuth() {
        return resolve("compass.cardinal.azimuth");
    }

    public String ordinalDirectionWord() {
        return resolve("compass.ordinal.word");
    }

    public String ordinalDirectionAbbreviation() {
        return resolve("compass.ordinal.abbreviation");
    }

    public String ordinalDirectionAzimuth() {
        return resolve("compass.ordinal.azimuth");
    }

    public String halfWindDirectionWord() {
        return resolve("compass.half-wind.word");
    }

    public String halfWindDirectionAbbreviation() {
        return resolve("compass.half-wind.abbreviation");
    }

    public String halfWindDirectionAzimuth() {
        return resolve("compass.half-wind.azimuth");
    }

    public String quarterWindDirectionWord() {
        return resolve("compass.quarter-wind.word");
    }

    public String quarterWindDirectionAbbreviation() {
        return resolve("compass.quarter-wind.abbreviation");
    }

    public String quarterWindDirectionAzimuth() {
        return resolve("compass.quarter-wind.azimuth");
    }

}
