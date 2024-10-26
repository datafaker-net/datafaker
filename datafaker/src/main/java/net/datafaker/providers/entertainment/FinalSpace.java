package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Final Space is an adult animated space opera comedy drama television series.
 *
 * @since 1.6.0
 */
public class FinalSpace extends AbstractProvider<EntertainmentProviders> {

    protected FinalSpace(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("final_space.characters");
    }

    public String vehicle() {
        return resolve("final_space.vehicles");
    }

    public String quote() {
        return resolve("final_space.quotes");
    }

}
