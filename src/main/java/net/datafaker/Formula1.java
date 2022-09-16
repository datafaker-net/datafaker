package net.datafaker;

/**
 * @since 1.2.0
 */
public class Formula1 extends AbstractProvider {

    protected Formula1(Faker faker) {
        super(faker);
    }

    public String driver() {
        return resolve("formula1.driver");
    }

    public String team() {
        return resolve("formula1.team");
    }

    public String circuit() {
        return resolve("formula1.circuit");
    }

    public String grandPrix() {
        return resolve("formula1.grand_prix");
    }
}
