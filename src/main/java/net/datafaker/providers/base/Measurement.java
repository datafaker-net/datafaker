package net.datafaker.providers.base;

/**
 * @since 1.5.0
 */
public class Measurement extends AbstractProvider<BaseProviders> {

    protected Measurement(BaseProviders faker) {
        super(faker);
    }

    /**
     * This method generates a random height measurement.
     *
     * @return a string of height measurement.
     */
    public String height() {
        return resolve("measurement.height");
    }

    /**
     * This method generates a random length measurement.
     *
     * @return a string of length measurement.
     */
    public String length() {
        return resolve("measurement.length");
    }

    /**
     * This method generates a random volume measurement.
     *
     * @return a string of volume measurement.
     */
    public String volume() {
        return resolve("measurement.volume");
    }

    /**
     * This method generates a random weight measurement.
     *
     * @return a string of weight measurement.
     */
    public String weight() {
        return resolve("measurement.weight");
    }

    /**
     * This method generates a random metric height measurement.
     *
     * @return a string of metric height measurement.
     */
    public String metricHeight() {
        return resolve("measurement.metric_height");
    }

    /**
     * This method generates a random metric length measurement.
     *
     * @return a string of metric length measurement.
     */
    public String metricLength() {
        return resolve("measurement.metric_length");
    }

    /**
     * This method generates a random metric volume measurement.
     *
     * @return a string of metric volume measurement.
     */
    public String metricVolume() {
        return resolve("measurement.metric_volume");
    }

    /**
     * This method generates a random metric weight measurement.
     *
     * @return a string of metric weight measurement.
     */
    public String metricWeight() {
        return resolve("measurement.metric_weight");
    }
}
