package net.datafaker;

/**
 * K-pop, short for Korean popular music, is a genre of music originating in South Korea as part of South Korean culture.
 *
 * @since 1.3.0
 */
public class Kpop {

    private final Faker faker;

    protected Kpop(Faker faker) {
        this.faker = faker;
    }

    public String iGroups() {
        return faker.fakeValuesService().resolve("kpop.i_groups", this, faker);
    }

    public String iiGroups() {
        return faker.fakeValuesService().resolve("kpop.ii_groups", this, faker);
    }

    public String iiiGroups() {
        return faker.fakeValuesService().resolve("kpop.iii_groups", this, faker);
    }

    public String girlGroups() {
        return faker.fakeValuesService().resolve("kpop.girl_groups", this, faker);
    }

    public String boyBands() {
        return faker.fakeValuesService().resolve("kpop.boy_bands", this, faker);
    }

    public String solo() {
        return faker.fakeValuesService().resolve("kpop.solo", this, faker);
    }

}
