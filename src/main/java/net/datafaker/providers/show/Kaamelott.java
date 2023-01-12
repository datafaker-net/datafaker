package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Kaamelott extends AbstractProvider<ShowProviders> {

    protected Kaamelott(ShowProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("kaamelott.characters");
    }

    public String quote() {
        return resolve("kaamelott.quotes");
    }
}
