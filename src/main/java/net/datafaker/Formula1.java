package net.datafaker;

/**
 * @since 0.8.0
 */
public class Formula1 {
    private final Faker faker;

    protected Formula1(Faker faker) {
        this.faker = faker;
    }

    public String driver() {
        return faker.fakeValuesService().resolve("formula1.driver", this, faker);
    }

    public String team() {
        return faker.fakeValuesService().resolve("formula1.team", this, faker);
    }

    public String circuit() {
        return faker.fakeValuesService().resolve("formula1.circuit", this, faker);
    }

    public String grandPrix() {
        return faker.fakeValuesService().resolve("formula1.grand_prix", this, faker);
    }
}
