package net.datafaker;

public class Fallout extends AbstractProvider {

    protected Fallout(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("fallout.characters");
    }

    public String faction() {
        return faker.resolve("fallout.factions");
    }

    public String location() {
        return faker.resolve("fallout.locations");
    }

    public String quote() {
        return faker.resolve("fallout.quotes");
    }
}
