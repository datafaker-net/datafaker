package net.datafaker;

/**
 * @since 1.4.0
 */
public class Camera extends AbstractProvider {

    protected Camera(Faker faker) {
        super(faker);
    }

    /**
     * This method generates a random camera brand.
     *
     * @return a string of camera brand.
     */
    public String brand() {
        return faker.fakeValuesService().resolve("camera.brand", this);
    }

    /**
     * This method generates a random camera model.
     *
     * @return a string of camera model.
     */
    public String model() {
        return faker.fakeValuesService().resolve("camera.model", this);
    }

    /**
     * This method generates a random camera brand with a model.
     *
     * @return a string of camera brand with a model.
     */
    public String brandWithModel() {
        return faker.fakeValuesService().resolve("camera.brand_with_model", this);
    }
}
