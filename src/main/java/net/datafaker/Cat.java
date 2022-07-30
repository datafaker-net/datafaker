package net.datafaker;

/**
 * @since 0.8.0
 */
public class Cat extends AbstractProvider {

    protected Cat(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("creature.cat.name", this);
    }

    public String breed() {
        return faker.fakeValuesService().resolve("creature.cat.breed", this);
    }

    public String registry() {
        return faker.fakeValuesService().resolve("creature.cat.registry", this);
    }
}
