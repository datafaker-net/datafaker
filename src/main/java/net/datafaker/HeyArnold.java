package net.datafaker;

/**
 * Hey Arnold! is an American animated comedy television series created by Craig Bartlett.
 *
 * @since 1.4.0
 */
public class HeyArnold extends AbstractProvider {

    protected HeyArnold(Faker faker) {
        super(faker);
    }

    public String characters() {
        return faker.fakeValuesService().resolve("hey_arnold.characters", this);
    }

    public String locations() {
        return faker.fakeValuesService().resolve("hey_arnold.locations", this);
    }

    public String quotes() {
        return faker.fakeValuesService().resolve("hey_arnold.quotes", this);
    }

}
