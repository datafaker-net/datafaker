package net.datafaker;

/**
 * @since 1.4.0
 */
public class Camera extends AbstractProvider<IProviders> {

    protected Camera(BaseFaker faker) {
        super(faker);
    }

    /**
     * This method generates a random camera brand.
     *
     * @return a string of camera brand.
     */
    public String brand() {
        return resolve("camera.brand");
    }

    /**
     * This method generates a random camera model.
     *
     * @return a string of camera model.
     */
    public String model() {
        return resolve("camera.model");
    }

    /**
     * This method generates a random camera brand with a model.
     *
     * @return a string of camera brand with a model.
     */
    public String brandWithModel() {
        return resolve("camera.brand_with_model");
    }
}
