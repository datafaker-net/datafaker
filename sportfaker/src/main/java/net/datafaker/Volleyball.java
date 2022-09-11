package net.datafaker;

/**
 * @since 1.3.0
 */
public class Volleyball extends AbstractProvider<SportFaker> {

    protected Volleyball(SportFaker faker) {
        super(faker);
    }

    public String team() {
        return faker.fakeValuesService().resolve("volleyball.team", this);
    }

    public String player() {
        return faker.fakeValuesService().resolve("volleyball.player", this);
    }

    public String coach() {
        return faker.fakeValuesService().resolve("volleyball.coach", this);
    }

    public String position() {
        return faker.fakeValuesService().resolve("volleyball.position", this);
    }

    public String formation() {
        return faker.fakeValuesService().resolve("volleyball.formation", this);
    }

}
