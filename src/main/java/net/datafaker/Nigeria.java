package net.datafaker;

/**
 * Nigeria, officially the Federal Republic of Nigeria, is a country in West Africa.
 *
 * @since 1.2.0
 */
public class Nigeria extends AbstractProvider {
    private static final String KEY = "nigeria";

    protected Nigeria(Faker faker) {
        super(faker);
    }

    public String places() {
        return faker.fakeValuesService().resolve(KEY + ".places", this);
    }

    public String name() {
        return faker.fakeValuesService().resolve(KEY + ".name", this);
    }

    public String food() {
        return faker.fakeValuesService().resolve(KEY + ".food", this);
    }

    public String schools() {
        return faker.fakeValuesService().resolve(KEY + ".schools", this);
    }

    public String celebrities() {
        return faker.fakeValuesService().resolve(KEY + ".celebrities", this);
    }
}



