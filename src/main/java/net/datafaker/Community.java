package net.datafaker;

public class Community extends AbstractProvider {

    protected Community(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("community.characters");
    }

    public String quote() {
        return faker.resolve("community.quotes");
    }
}
