package net.datafaker;

/**
 * @since 1.5.0
 */
public class Football extends AbstractProvider {

    protected Football(Faker faker) {
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
