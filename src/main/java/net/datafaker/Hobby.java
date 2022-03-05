package net.datafaker;

/**
 * @since 1.3.0
 */
public class Hobby {

    private final Faker faker;

    protected Hobby(Faker faker) {
        this.faker = faker;
    }

    public String activity() {
        return faker.fakeValuesService().resolve("hobby.activity", this, faker);
    }

}
