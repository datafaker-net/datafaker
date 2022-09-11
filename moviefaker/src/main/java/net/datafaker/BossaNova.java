package net.datafaker;

/**
 * Bossa nova is a style of samba developed in the late 1950s and early 1960s in Rio de Janeiro, Brazil.
 * It is mainly characterized by a "different beat" that altered the harmonies with the introduction of
 * unconventional chords and an innovative syncopation of traditional samba from a single rhythmic division.
 *
 * @since 1.0.0
 */
public class BossaNova extends MovieProvider {

    protected BossaNova(MovieFaker faker) {
        super(faker);
    }

    public String artist() {
        return faker.fakeValuesService().resolve("bossa_nova.artists", this);
    }

    public String song() {
        return faker.fakeValuesService().resolve("bossa_nova.songs", this);
    }
}
