package net.datafaker;

/**
 * @since 1.5.0
 */
class Football {

    private final Faker faker;

    protected Football(Faker faker) {
        this.faker = faker;
    }

    public String teams() {
        return faker.fakeValuesService().resolve("football.teams", this, faker);
    }

    public String players() {
        return faker.fakeValuesService().resolve("football.players", this, faker);
    }

    public String coaches() {
        return faker.fakeValuesService().resolve("football.coaches", this, faker);
    }

    public String competitions() {
        return faker.fakeValuesService().resolve("football.competitions", this, faker);
    }

    public String positions() {
        return faker.fakeValuesService().resolve("football.positions", this, faker);
    }

}
