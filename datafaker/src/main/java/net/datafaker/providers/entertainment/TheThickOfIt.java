package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.8.0
 */
public class TheThickOfIt extends AbstractProvider<EntertainmentProviders> {

    protected TheThickOfIt(EntertainmentProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("the_thick_of_it.characters");
    }

    public String positions() {
        return resolve("the_thick_of_it.positions");
    }

    public String departments() {
        return resolve("the_thick_of_it.departments");
    }

}
