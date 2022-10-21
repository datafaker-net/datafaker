package net.datafaker.providers.sport;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.3.0
 */
public class Volleyball extends AbstractProvider<SportProviders> {

    protected Volleyball(SportProviders faker) {
        super(faker);
    }

    public String team() {
        return resolve("volleyball.team");
    }

    public String player() {
        return resolve("volleyball.player");
    }

    public String coach() {
        return resolve("volleyball.coach");
    }

    public String position() {
        return resolve("volleyball.position");
    }

    public String formation() {
        return resolve("volleyball.formation");
    }

}
