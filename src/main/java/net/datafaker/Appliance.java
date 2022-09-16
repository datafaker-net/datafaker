package net.datafaker;

/**
 * @since 1.0.0
 */
public class Appliance extends AbstractProvider {

    protected Appliance(Faker faker) {
        super(faker);
    }

    public String brand() {
        return resolve("appliance.brand");
    }

    public String equipment() {
        return resolve("appliance.equipment");
    }
}
