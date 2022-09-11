package net.datafaker;

/**
 * @since 1.2.0
 */
public class Formula1 extends AbstractProvider<SportFaker> {

    protected Formula1(SportFaker faker) {
        super(faker);
    }

    public String driver() {
        return faker.fakeValuesService().resolve("formula1.driver", this);
    }

    public String team() {
        return faker.fakeValuesService().resolve("formula1.team", this);
    }

    public String circuit() {
        return faker.fakeValuesService().resolve("formula1.circuit", this);
    }

    public String grandPrix() {
        return faker.fakeValuesService().resolve("formula1.grand_prix", this);
    }
}
