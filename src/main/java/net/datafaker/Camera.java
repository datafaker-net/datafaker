package net.datafaker;

/**
 * @since 1.4.0
 */
public class Camera {

    private final Faker faker;

    protected Camera(Faker faker) {
        this.faker = faker;
    }

    /**
     * This method generates a random camera brand.
     *
     * @return a string of camera brand.
     */
    public String brand() {
        return faker.fakeValuesService().resolve("camera.brand", this, faker);
    }

    /**
     * This method generates a random camera model.
     *
     * @return a string of camera model.
     */
    public String model() {
        return faker.fakeValuesService().resolve("camera.model", this, faker);
    }

    /**
     * This method generates a random camera brand with a model.
     *
     * @return a string of camera brand with a model.
     */
    public String brandWithModel() {
        return faker.fakeValuesService().resolve("camera.brand_with_model", this, faker);
    }
}
