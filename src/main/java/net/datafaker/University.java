package net.datafaker;

/**
 * @since 0.8.0
 */
public class University extends AbstractProvider {

    protected University(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("university.name", this);
    }

    public String prefix() {
        return faker.fakeValuesService().resolve("university.prefix", this);
    }

    public String suffix() {
        return faker.fakeValuesService().resolve("university.suffix", this);
    }
}
