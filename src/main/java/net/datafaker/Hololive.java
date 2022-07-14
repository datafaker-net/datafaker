package net.datafaker;

public class Hololive {
    private final Faker faker;

    protected Hololive(Faker faker) {
        this.faker = faker;
    }

    public String talent() {
        return faker.resolve("hololive.talent");
    }
}
