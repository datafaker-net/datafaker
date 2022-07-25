package net.datafaker;

/**
 * @since 1.5.0
 */
public class Measurement {

    private final Faker faker;

    protected Measurement(Faker faker) {
        this.faker = faker;
    }

    /**
     * This method generates a random height measurement.
     *
     * @return a string of height measurement.
     */
    public String height() {
        return faker.fakeValuesService().resolve("measurement.height", this, faker);
    }

    /**
     * This method generates a random length measurement.
     *
     * @return a string of length measurement.
     */
    public String length() {
        return faker.fakeValuesService().resolve("measurement.length", this, faker);
    }

    /**
     * This method generates a random volume measurement.
     *
     * @return a string of volume measurement.
     */
    public String volume() {
        return faker.fakeValuesService().resolve("measurement.volume", this, faker);
    }

    /**
     * This method generates a random weight measurement.
     *
     * @return a string of weight measurement.
     */
    public String weight() {
        return faker.fakeValuesService().resolve("measurement.weight", this, faker);
    }

    /**
     * This method generates a random metric height measurement.
     *
     * @return a string of metric height measurement.
     */
    public String metricHeight() {
        return faker.fakeValuesService().resolve("measurement.metric_height", this, faker);
    }

    /**
     * This method generates a random metric length measurement.
     *
     * @return a string of metric length measurement.
     */
    public String metricLength() {
        return faker.fakeValuesService().resolve("measurement.metric_length", this, faker);
    }

    /**
     * This method generates a random metric volume measurement.
     *
     * @return a string of metric volume measurement.
     */
    public String metricVolume() {
        return faker.fakeValuesService().resolve("measurement.metric_volume", this, faker);
    }

    /**
     * This method generates a random metric weight measurement.
     *
     * @return a string of metric weight measurement.
     */
    public String metricWeight() {
        return faker.fakeValuesService().resolve("measurement.metric_weight", this, faker);
    }
}
