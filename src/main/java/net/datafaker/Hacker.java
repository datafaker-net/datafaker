package net.datafaker;

/**
 * @since 0.8.0
 */
public class Hacker extends AbstractProvider {

    protected Hacker(Faker faker) {
        super(faker);
    }

    public String abbreviation() {
        return faker.fakeValuesService().resolve("hacker.abbreviation", this);
    }

    public String adjective() {
        return faker.fakeValuesService().resolve("hacker.adjective", this);
    }

    public String noun() {
        return faker.fakeValuesService().resolve("hacker.noun", this);
    }

    public String verb() {
        return faker.fakeValuesService().resolve("hacker.verb", this);
    }

    public String ingverb() {
        return faker.fakeValuesService().resolve("hacker.ingverb", this);
    }
}
