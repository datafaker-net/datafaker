package net.datafaker;

/**
 * Military ranks.
 *
 * @since 1.2.0
 */
public class Military {

    private final Faker faker;

    protected Military(Faker faker) {
        this.faker = faker;
    }

    public String armyRank() {
        return faker.fakeValuesService().resolve("military.army_rank", this, faker);
    }

    public String marinesRank() {
        return faker.fakeValuesService().resolve("military.marines_rank", this, faker);
    }

    public String navyRank() {
        return faker.fakeValuesService().resolve("military.navy_rank", this, faker);
    }

    public String airForceRank() {
        return faker.fakeValuesService().resolve("military.air_force_rank", this, faker);
    }

    public String dodPaygrade() {
        return faker.fakeValuesService().resolve("military.dod_paygrade", this, faker);
    }

}
