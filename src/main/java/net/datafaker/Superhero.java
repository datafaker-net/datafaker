package net.datafaker;

/**
 * @since 0.8.0
 */
public class Superhero extends AbstractProvider {

    protected Superhero(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("superhero.name", this);
    }

    public String prefix() {
        return faker.fakeValuesService().resolve("superhero.prefix", this);
    }

    public String suffix() {
        return faker.fakeValuesService().resolve("superhero.suffix", this);
    }

    public String power() {
        return faker.fakeValuesService().resolve("superhero.power", this);
    }

    public String descriptor() {
        return faker.fakeValuesService().resolve("superhero.descriptor", this);
    }
}
