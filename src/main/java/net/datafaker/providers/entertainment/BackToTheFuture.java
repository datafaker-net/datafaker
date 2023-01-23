package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class BackToTheFuture extends AbstractProvider<EntertainmentProviders> {

    protected BackToTheFuture(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("back_to_the_future.characters");
    }

    public String date() {
        return resolve("back_to_the_future.dates");
    }

    public String quote() {
        return resolve("back_to_the_future.quotes");
    }
}
