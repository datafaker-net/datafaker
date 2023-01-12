package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

/**
 * Stargate is a military science fiction media franchise.
 *
 * @since 1.8.0
 */
public class Stargate extends AbstractProvider<BaseProviders> {

    protected Stargate(BaseProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("stargate.characters");
    }

    public String planets() {
        return resolve("stargate.planets");
    }

    public String quotes() {
        return resolve("stargate.quotes");
    }

}
