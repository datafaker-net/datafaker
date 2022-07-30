package net.datafaker;

/**
 * @since 1.3.0
 */
public class Hobby extends AbstractProvider {

    protected Hobby(Faker faker) {
        super(faker);
    }

    public String activity() {
        return faker.fakeValuesService().resolve("hobby.activity", this);
    }

}
