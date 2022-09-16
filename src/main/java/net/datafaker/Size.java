package net.datafaker;

/**
 * @since 0.8.0
 */
public class Size extends AbstractProvider {

    protected Size(Faker faker) {
        super(faker);
    }

    public String adjective() {
        return resolve("size.adjective");
    }

}
