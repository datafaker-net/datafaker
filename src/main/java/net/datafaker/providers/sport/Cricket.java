package net.datafaker.providers.sport;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.7.0
 */
public class Cricket extends AbstractProvider<SportProviders> {

    protected Cricket(SportProviders faker) {
        super(faker);
    }

    public String teams() {
        return resolve("cricket.teams");
    }

    public String players() {
        return resolve("cricket.players");
    }

    public String formats() {
        return resolve("cricket.formats");
    }

    public String tournaments() {
        return resolve("cricket.tournaments");
    }

}
