package net.datafaker;

/**
 * @since 1.5.0
 */
public class Ghostbusters extends AbstractProvider {

    protected Ghostbusters(Faker faker) {
        super(faker);
    }

    public String actor() {
        return faker.resolve("ghostbusters.actors");
    }

    public String character() {
        return faker.resolve("ghostbusters.characters");
    }

    public String quote() {
        return faker.resolve("ghostbusters.quotes");
    }
}
