package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Death Note is a Japanese manga series written by Tsugumi Ohba and illustrated by Takeshi Obata.
 *
 * @since 2.6.0
 */
public class DeathNote extends AbstractProvider<EntertainmentProviders> {

    protected DeathNote(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("death_note.characters");
    }

    public String location() {
        return resolve("death_note.locations");
    }

    public String quote() {
        return resolve("death_note.quotes");
    }

    public String shinigami() {
        return resolve("death_note.shinigami");
    }
}
