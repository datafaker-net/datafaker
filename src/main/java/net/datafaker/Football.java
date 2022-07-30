package net.datafaker;

/**
 * @since 1.5.0
 */
public class Football extends AbstractProvider {

    protected Football(Faker faker) {
        super(faker);
    }

    public String teams() {
        return faker.fakeValuesService().resolve("football.teams", this);
    }

    public String players() {
        return faker.fakeValuesService().resolve("football.players", this);
    }

    public String coaches() {
        return faker.fakeValuesService().resolve("football.coaches", this);
    }

    public String competitions() {
        return faker.fakeValuesService().resolve("football.competitions", this);
    }

    public String positions() {
        return faker.fakeValuesService().resolve("football.positions", this);
    }

}
