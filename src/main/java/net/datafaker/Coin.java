package net.datafaker;

/**
 * @since 0.8.0
 */
public class Coin extends AbstractProvider {

    protected Coin(Faker faker) {
        super(faker);
    }

    /**
     * @return coin side e.g. "Heads", "Tails".
     */
    public String flip() {
        return faker.fakeValuesService().resolve("coin.flip", this);
    }
}
