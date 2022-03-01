package net.datafaker;

/**
 * Generates marketing buzzwords.
 *
 * @since 1.2.0
 */
public class Marketing {

    private final Faker faker;

    protected Marketing(Faker faker) {
        this.faker = faker;
    }

    public String buzzwords() {
        return faker.fakeValuesService().resolve("marketing.buzzwords", this, faker);
    }
}
