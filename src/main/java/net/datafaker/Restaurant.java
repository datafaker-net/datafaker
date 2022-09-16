package net.datafaker;

/**
 * @since 1.2.0
 */
public class Restaurant extends AbstractProvider {

    protected Restaurant(Faker faker) {
        super(faker);
    }

    public String namePrefix() {
        return resolve("restaurant.name_prefix");
    }

    public String nameSuffix() {
        return resolve("restaurant.name_suffix");
    }

    public String name() {
        return resolve("restaurant.name");
    }

    public String type() {
        return resolve("restaurant.type");
    }

    public String description() {
        return resolve("restaurant.description");
    }

    public String review() {
        return resolve("restaurant.review");
    }

}
