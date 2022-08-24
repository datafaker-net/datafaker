package net.datafaker;

/**
 * Fallout: A Post Nuclear Role Playing Game is a 1997 role-playing video game developed and published by Interplay Productions.
 *
 * @since 1.6.0
 */
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
