package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.7.0
 */
public class FullmetalAlchemist extends AbstractProvider<EntertainmentProviders> {

    protected FullmetalAlchemist(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("fma_brotherhood.characters");
    }

    public String city() {
        return resolve("fma_brotherhood.cities");
    }

    public String country() {
        return resolve("fma_brotherhood.countries");
    }

}
