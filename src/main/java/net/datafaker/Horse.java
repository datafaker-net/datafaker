package net.datafaker;

/**
 * @since 1.3.0
 */
public class Horse extends AbstractProvider {

    protected Horse(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("creature.horse.name", this);
    }

    public String breed() {
        return faker.fakeValuesService().resolve("creature.horse.breed", this);
    }

}
