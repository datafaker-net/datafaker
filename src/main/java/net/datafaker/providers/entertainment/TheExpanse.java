package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * The Expanse is an American science fiction television series developed by Mark Fergus and Hawk Ostby for the Syfy network.
 *
 * @since 1.8.0
 */
public class TheExpanse extends AbstractProvider<EntertainmentProviders> {

    protected TheExpanse(EntertainmentProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("the_expanse.characters");
    }

    public String locations() {
        return resolve("the_expanse.locations");
    }

    public String ships() {
        return resolve("the_expanse.ships");
    }

    public String quotes() {
        return resolve("the_expanse.quotes");
    }

}
