package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Team extends AbstractProvider<BaseProviders> {

    protected Team(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("team.name");
    }

    public String creature() {
        return resolve("team.creature");
    }

    public String state() {
        return resolve("address.state");
    }

    public String sport() {
        return resolve("team.sport");
    }
}
