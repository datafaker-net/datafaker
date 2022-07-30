package net.datafaker;

/**
 * @since 1.5.0
 */
public class Simpsons extends AbstractProvider {

    public Simpsons(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("simpsons.characters");
    }

    public String location() {
        return faker.resolve("simpsons.locations");
    }

    public String quote() {
        return faker.resolve("simpsons.quotes");
    }
}
