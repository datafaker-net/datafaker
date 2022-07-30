package net.datafaker;

/**
 * Generates marketing buzzwords.
 *
 * @since 1.2.0
 */
public class Marketing extends AbstractProvider {

    protected Marketing(Faker faker) {
        super(faker);
    }

    public String buzzwords() {
        return faker.fakeValuesService().resolve("marketing.buzzwords", this);
    }
}
