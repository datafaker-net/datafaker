package net.datafaker;

/**
 * @since 1.3.0
 */
public class SuperMario {

    private final Faker faker;

    protected SuperMario(Faker faker) {
        this.faker = faker;
    }

    public String characters() {
        return faker.fakeValuesService().resolve("games.super_mario.characters", this, faker);
    }

    public String games() {
        return faker.fakeValuesService().resolve("games.super_mario.games", this, faker);
    }

    public String locations() {
        return faker.fakeValuesService().resolve("games.super_mario.locations", this, faker);
    }

}
