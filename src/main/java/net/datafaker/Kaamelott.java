package net.datafaker;

/**
 * @since 0.8.0
 */
public class Kaamelott {
    private final Faker faker;

    protected Kaamelott(Faker faker) {
        this.faker = faker;
    }

    public String character() {
        return faker.fakeValuesService().resolve("kaamelott.characters", this, faker);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("kaamelott.quotes", this, faker);
    }
}
