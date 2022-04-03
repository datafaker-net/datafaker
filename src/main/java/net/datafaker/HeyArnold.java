package net.datafaker;

/**
 * Hey Arnold! is an American animated comedy television series created by Craig Bartlett.
 *
 * @since 1.4.0
 */
public class HeyArnold {

    private final Faker faker;

    protected HeyArnold(Faker faker) {
        this.faker = faker;
    }

    public String characters() {
        return faker.fakeValuesService().resolve("hey_arnold.characters", this, faker);
    }

    public String locations() {
        return faker.fakeValuesService().resolve("hey_arnold.locations", this, faker);
    }

    public String quotes() {
        return faker.fakeValuesService().resolve("hey_arnold.quotes", this, faker);
    }

}
