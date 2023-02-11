package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.8.0
 */
public class Show extends AbstractProvider<EntertainmentProviders> {

    protected Show(EntertainmentProviders faker) {
        super(faker);
    }

    public String adultMusical() {
        return resolve("show.adult_musical");
    }

    public String play() {
        return resolve("show.play");
    }

    public String kidsMusical() {
        return resolve("show.kids_musical");
    }

}
