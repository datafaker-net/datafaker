package net.datafaker;

/**
 * @since 1.3.0
 */
public class SuperMario extends AbstractProvider {

    protected SuperMario(Faker faker) {
        super(faker);
    }

    public String characters() {
        return faker.fakeValuesService().resolve("games.super_mario.characters", this);
    }

    public String games() {
        return faker.fakeValuesService().resolve("games.super_mario.games", this);
    }

    public String locations() {
        return faker.fakeValuesService().resolve("games.super_mario.locations", this);
    }

}
