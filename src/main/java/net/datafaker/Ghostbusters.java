package net.datafaker;

/**
 * @since 1.5.0
 */
public class Ghostbusters extends AbstractProvider {

    protected Ghostbusters(Faker faker) {
        super(faker);
    }

    public String actor() {
        return resolve("ghostbusters.actors");
    }

    public String character() {
        return resolve("ghostbusters.characters");
    }

    public String quote() {
        return resolve("ghostbusters.quotes");
    }
}
