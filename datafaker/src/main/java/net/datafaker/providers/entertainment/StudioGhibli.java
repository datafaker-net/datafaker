package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.7.0
 */
public class StudioGhibli extends AbstractProvider<EntertainmentProviders> {

    protected StudioGhibli(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("studio_ghibli.characters");
    }

    public String quote() {
        return resolve("studio_ghibli.quotes");
    }

    public String movie() {
        return resolve("studio_ghibli.movies");
    }

}
