package net.datafaker;

/**
 * @since 0.8.0
 */
public class Overwatch extends AbstractProvider {

    protected Overwatch(Faker faker) {
        super(faker);
    }

    public String hero() {
        return resolve("games.overwatch.heroes");
    }

    public String location() {
        return resolve("games.overwatch.locations");
    }

    public String quote() {
        return resolve("games.overwatch.quotes");
    }
}
