package net.datafaker;

/**
 * Military ranks.
 *
 * @since 1.2.0
 */
public class Military extends AbstractProvider {

    protected Military(Faker faker) {
        super(faker);
    }

    public String armyRank() {
        return faker.fakeValuesService().resolve("military.army_rank", this);
    }

    public String marinesRank() {
        return faker.fakeValuesService().resolve("military.marines_rank", this);
    }

    public String navyRank() {
        return faker.fakeValuesService().resolve("military.navy_rank", this);
    }

    public String airForceRank() {
        return faker.fakeValuesService().resolve("military.air_force_rank", this);
    }

    public String dodPaygrade() {
        return faker.fakeValuesService().resolve("military.dod_paygrade", this);
    }

}
