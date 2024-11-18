package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.5.0
 */
public class Movie extends AbstractProvider<EntertainmentProviders> {

    protected Movie(EntertainmentProviders faker) {
        super(faker);
    }

    public String quote() {
        return resolve("movie.quote");
    }
    
    public String name() {
        return resolve("movie.name");
    }
    
}
