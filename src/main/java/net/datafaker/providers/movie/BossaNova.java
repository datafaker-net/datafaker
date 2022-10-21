package net.datafaker.providers.movie;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Bossa nova is a style of samba developed in the late 1950s and early 1960s in Rio de Janeiro, Brazil.
 * It is mainly characterized by a "different beat" that altered the harmonies with the introduction of
 * unconventional chords and an innovative syncopation of traditional samba from a single rhythmic division.
 *
 * @since 1.0.0
 */
public class BossaNova extends AbstractProvider<MovieProviders> {

    protected BossaNova(MovieProviders faker) {
        super(faker);
    }

    public String artist() {
        return resolve("bossa_nova.artists");
    }

    public String song() {
        return resolve("bossa_nova.songs");
    }
}
