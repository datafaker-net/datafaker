package net.datafaker;

/**
 * @since 0.8.0
 */
public class Coin {

    private final Faker faker;

    protected Coin(Faker faker) {
        this.faker = faker;
    }

    /**
     * @return coin side e.g. "Heads", "Tails".
     */
    public String flip() {
        return faker.fakeValuesService().resolve("coin.flip", this, faker);
    }
}
