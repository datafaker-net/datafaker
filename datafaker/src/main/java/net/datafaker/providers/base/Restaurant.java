package net.datafaker.providers.base;

/**
 * @since 1.2.0
 */
public class Restaurant extends AbstractProvider<BaseProviders> {

    protected Restaurant(BaseProviders faker) {
        super(faker);
    }

    public String namePrefix() {
        return faker.bothify(resolve("restaurant.name_prefix"), true);
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
