package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Overwatch extends AbstractProvider<IProviders> {

    protected Overwatch(BaseFaker faker) {
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
