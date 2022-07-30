package net.datafaker;

/**
 * @since 0.8.0
 */
public class FunnyName extends AbstractProvider {

    protected FunnyName(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("funny_name.name", this);
    }
}
