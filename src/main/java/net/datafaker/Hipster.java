package net.datafaker;

/**
 * @since 0.8.0
 */
public class Hipster extends AbstractProvider {

    protected Hipster(final Faker faker) {
        super(faker);
    }

    public String word() {
        return faker.resolve("hipster.words");
    }
}
