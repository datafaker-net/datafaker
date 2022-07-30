package net.datafaker;

/**
 * @since 0.8.0
 */
public class Team extends AbstractProvider {

    protected Team(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("team.name", this);
    }

    public String creature() {
        return faker.fakeValuesService().resolve("team.creature", this);
    }

    public String state() {
        return faker.fakeValuesService().resolve("address.state", this);
    }

    public String sport() {
        return faker.fakeValuesService().resolve("team.sport", this);
    }
}
