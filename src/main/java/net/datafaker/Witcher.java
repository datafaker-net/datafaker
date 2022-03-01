package net.datafaker;

/**
 * @since 0.8.0
 */
public class Witcher {
    private final Faker faker;

    protected Witcher(Faker faker) {
        this.faker = faker;
    }

    public String character() {
        return faker.fakeValuesService().resolve("games.witcher.characters", this, faker);
    }

    public String witcher() {
        return faker.fakeValuesService().resolve("games.witcher.witchers", this, faker);
    }

    public String school() {
        return faker.fakeValuesService().resolve("games.witcher.schools", this, faker);
    }

    public String location() {
        return faker.fakeValuesService().resolve("games.witcher.locations", this, faker);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("games.witcher.quotes", this, faker);
    }

    public String monster() {
        return faker.fakeValuesService().resolve("games.witcher.monsters", this, faker);
    }
    public String sign() {
        return faker.fakeValuesService().resolve("games.witcher.signs", this, faker);
    }

    public String potion() {
        return faker.fakeValuesService().resolve("games.witcher.potions", this, faker);
    }

    public String book() {
        return faker.fakeValuesService().resolve("games.witcher.books", this, faker);
    }
}
