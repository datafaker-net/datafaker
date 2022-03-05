package net.datafaker;

/**
 * @since 1.3.0
 */
public class Horse {

    private final Faker faker;

    protected Horse(Faker faker) {
        this.faker = faker;
    }

    public String name() {
        return faker.fakeValuesService().resolve("creature.horse.name", this, faker);
    }

    public String breed() {
        return faker.fakeValuesService().resolve("creature.horse.breed", this, faker);
    }

}
