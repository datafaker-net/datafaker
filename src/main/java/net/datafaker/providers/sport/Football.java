package net.datafaker.providers.sport;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.5.0
 */
public class Football extends AbstractProvider<SportProviders> {

    protected Football(SportProviders faker) {
        super(faker);
    }

    public String teams() {
        return resolve("football.teams");
    }

    public String players() {
        return resolve("football.players");
    }

    public String coaches() {
        return resolve("football.coaches");
    }

    public String competitions() {
        return resolve("football.competitions");
    }

    public String positions() {
        return resolve("football.positions");
    }

}
