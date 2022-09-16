package net.datafaker;

/**
 * @since 0.8.0
 */
public class Buffy extends AbstractProvider {

    protected Buffy(Faker faker) {
        super(faker);
    }

    public String characters() {
        return resolve("buffy.characters");
    }

    public String quotes() {
        return resolve("buffy.quotes");
    }

    public String celebrities() {
        return resolve("buffy.celebrities");
    }

    public String bigBads() {
        return resolve("buffy.big_bads");
    }

    public String episodes() {
        return resolve("buffy.episodes");
    }

}
