package net.datafaker;

/**
 * @since 0.8.0
 */
public class Witcher extends AbstractProvider {

    protected Witcher(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.fakeValuesService().resolve("games.witcher.characters", this);
    }

    public String witcher() {
        return faker.fakeValuesService().resolve("games.witcher.witchers", this);
    }

    public String school() {
        return faker.fakeValuesService().resolve("games.witcher.schools", this);
    }

    public String location() {
        return faker.fakeValuesService().resolve("games.witcher.locations", this);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("games.witcher.quotes", this);
    }

    public String monster() {
        return faker.fakeValuesService().resolve("games.witcher.monsters", this);
    }
    public String sign() {
        return faker.fakeValuesService().resolve("games.witcher.signs", this);
    }

    public String potion() {
        return faker.fakeValuesService().resolve("games.witcher.potions", this);
    }

    public String book() {
        return faker.fakeValuesService().resolve("games.witcher.books", this);
    }
}
