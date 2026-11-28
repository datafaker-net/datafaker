package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Tormenta is a famous Brazilian RPG created in 1999 by Marcelo Cassaro,
 * Rogério Saladino, and JM Trevisan.
 *
 * @since 2.6.0
 */
public class Tormenta extends AbstractProvider<VideoGameProviders> {

    protected Tormenta(VideoGameProviders faker) {
        super(faker);
    }

    public String bestiary() {
        return resolve("games.tormenta.bestiary");
    }

    public String names() {
        return resolve("games.tormenta.names");
    }

    public String cities() {
        return resolve("games.tormenta.cities");
    }

}
