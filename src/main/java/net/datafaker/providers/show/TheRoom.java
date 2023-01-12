package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * The Room is a 2003 American drama film written, produced, executive produced and directed by Tommy Wiseau.
 *
 * @since 1.8.0
 */
public class TheRoom extends AbstractProvider<ShowProviders> {

    protected TheRoom(ShowProviders faker) {
        super(faker);
    }

    public String actors() {
        return resolve("the_room.actors");
    }

    public String characters() {
        return resolve("the_room.characters");
    }

    public String locations() {
        return resolve("the_room.locations");
    }

    public String quotes() {
        return resolve("the_room.quotes");
    }

}
