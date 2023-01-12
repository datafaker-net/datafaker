package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Dark Souls is a series of action role-playing games created by Hidetaka Miyazaki of FromSoftware and published by Bandai Namco Entertainment.
 *
 * @author SickDawn
 * @since 1.5.0
 */
public class DarkSouls extends AbstractProvider<VideoGameProviders> {

    public DarkSouls(final VideoGameProviders faker) {
        super(faker);
    }

    public String stats() {
        return resolve("dark_souls.stats");
    }

    public String covenants() {
        return resolve("dark_souls.covenants");
    }

    public String classes() {
        return resolve("dark_souls.classes");
    }

    public String shield() {
        return resolve("dark_souls.shield");
    }

}
