package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * V for Vendetta is a 2005 dystopian political action film directed by James McTeigue from a screenplay by the Wachowskis.
 *
 * @since 1.8.0
 */
public class VForVendetta extends AbstractProvider<EntertainmentProviders> {

    protected VForVendetta(EntertainmentProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("v_for_vendetta.characters");
    }

    public String speeches() {
        return resolve("v_for_vendetta.speeches");
    }

    public String quotes() {
        return resolve("v_for_vendetta.quotes");
    }

}
