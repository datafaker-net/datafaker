package net.datafaker;

/**
 * @since 1.4.0
 */
public class GratefulDead {

    private final Faker faker;

    protected GratefulDead(Faker faker) {
        this.faker = faker;
    }

    public String players() {
        return faker.fakeValuesService().resolve("grateful_dead.players", this, faker);
    }

    public String songs() {
        return faker.fakeValuesService().resolve("grateful_dead.songs", this, faker);
    }

}
