package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.7.0
 */
public class MoneyHeist extends AbstractProvider<EntertainmentProviders> {

    protected MoneyHeist(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("money_heist.characters");
    }

    public String heist() {
        return resolve("money_heist.heists");
    }

    public String quote() {
        return resolve("money_heist.quotes");
    }
}
