package net.datafaker.providers.sport;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.2.0
 */
public class Formula1 extends AbstractProvider<SportProviders> {

    protected Formula1(SportProviders faker) {
        super(faker);
    }

    public String driver() {
        return resolve("formula1.driver");
    }

    public String team() {
        return resolve("formula1.team");
    }

    public String circuit() {
        return resolve("formula1.circuit");
    }

    public String grandPrix() {
        return resolve("formula1.grand_prix");
    }
}
