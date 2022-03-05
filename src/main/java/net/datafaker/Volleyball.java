package net.datafaker;

/**
 * @since 1.3.0
 */
public class Volleyball {

    private final Faker faker;

    protected Volleyball(Faker faker) {
        this.faker = faker;
    }

    public String team() {
        return faker.fakeValuesService().resolve("volleyball.team", this, faker);
    }

    public String player() {
        return faker.fakeValuesService().resolve("volleyball.player", this, faker);
    }

    public String coach() {
        return faker.fakeValuesService().resolve("volleyball.coach", this, faker);
    }

    public String position() {
        return faker.fakeValuesService().resolve("volleyball.position", this, faker);
    }

    public String formation() {
        return faker.fakeValuesService().resolve("volleyball.formation", this, faker);
    }

}
