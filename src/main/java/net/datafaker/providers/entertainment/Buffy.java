package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Buffy extends AbstractProvider<EntertainmentProviders> {

    protected Buffy(EntertainmentProviders faker) {
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
