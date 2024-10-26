package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Hacker extends AbstractProvider<BaseProviders> {

    protected Hacker(BaseProviders faker) {
        super(faker);
    }

    public String abbreviation() {
        return resolve("hacker.abbreviation");
    }

    public String adjective() {
        return resolve("hacker.adjective");
    }

    public String noun() {
        return resolve("hacker.noun");
    }

    public String verb() {
        return resolve("hacker.verb");
    }

    public String ingverb() {
        return resolve("hacker.ingverb");
    }
}
