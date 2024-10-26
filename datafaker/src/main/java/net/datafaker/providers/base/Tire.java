package net.datafaker.providers.base;

/**
 * Sources:
 *      https://www.wheel-size.com/tire/
 *      https://en.wikipedia.org/wiki/Tire_code
 *
 * @since 2.0.2
 */
public class Tire extends AbstractProvider<BaseProviders> {

    protected Tire(BaseProviders faker) {
        super(faker);
    }

    /**
     * Returns a Tire Code, such as 205/60R16.
     * Which is width in mm / aspect ratio (height to width percentage) R (radial) Rim diameter.
     * 
     * @return a Tire Code String.
     */
    public String code() {
        return resolve("tire.code");
    }

    /**
     * Returns a Tire Code prefixed with the provided String.
     * 
     * @return a Tire Code string prefixed with the provided String.
     */
    public String code(String prefix) {
        return prefix + code();
    }

    /**
     * Returns a Tire Code optionally prefixed with the default prefix (P for passenger vehicle).
     * @param defaultPrefix if true the default prefix "P" will be included, otherwise it will not.
     * 
     * @return a Tire Code string, such as P205/60R16.
     */
    public String code(boolean defaultPrefix) {
        return defaultPrefix ? code("P") : code();
    }

    public String vehicleType() {
        return resolve("tire.vehicle_type");
    }

    public String width() {
        return resolve("tire.width");
    }

    public String aspectRatio() {
        return resolve("tire.aspect_ratio");
    }

    public String construction() {
        return resolve("tire.construction");
    }

    public String rimSize() {
        return resolve("tire.rim_size");
    }

    public String loadIndex() {
        return resolve("tire.load_index");
    }

    public String speedrating() {
        return resolve("tire.speed_rating");
    }

}
