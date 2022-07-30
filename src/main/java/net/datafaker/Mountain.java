package net.datafaker;

/**
 * A generator for Mountain names and ranges.
 *
 * @since 1.1.0
 */
public class Mountain extends AbstractProvider {

    protected Mountain(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("mountain.name", this);
    }

    public String range() {
        return faker.fakeValuesService().resolve("mountain.range", this);
    }
}
