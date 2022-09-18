package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Esports extends AbstractProvider<IProviders> {

    protected Esports(final BaseFaker faker) {
        super(faker);
    }

    public String player() {
        return resolve("esport.players");
    }

    public String team() {
        return resolve("esport.teams");
    }

    public String event() {
        return resolve("esport.events");
    }

    public String league() {
        return resolve("esport.leagues");
    }

    public String game() {
        return resolve("esport.games");
    }
}
