package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 *
 * @since 2.1.0
 */
public class Joke extends AbstractProvider<EntertainmentProviders> {

    protected Joke(final EntertainmentProviders faker) {
        super(faker);
    }

    public String pun() {
        return resolve("joke.puns");
    }

    public String knockKnock() {
        return resolve("joke.knock_knocks");
    }

}
