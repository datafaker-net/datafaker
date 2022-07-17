package net.datafaker;

public class Ghostbusters {

    private final Faker faker;

    protected Ghostbusters(Faker faker) {
        this.faker = faker;
    }

    public String actor() {
        return faker.resolve("ghostbusters.actors");
    }

    public String character() {
        return faker.resolve("ghostbusters.characters");
    }

    public String quote() {
        return faker.resolve("ghostbusters.quotes");
    }
}
