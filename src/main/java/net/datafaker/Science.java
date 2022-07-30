package net.datafaker;

/**
 * @since 0.8.0
 */
public class Science extends AbstractProvider {

    protected Science(Faker faker) {
        super(faker);
    }

    public String element() {
        return faker.fakeValuesService().resolve("science.element", this);
    }

    public String elementSymbol() {
        return faker.fakeValuesService().resolve("science.element_symbol", this);
    }

    public String scientist() {
        return faker.fakeValuesService().resolve("science.scientist", this);
    }

    public String tool() {
        return faker.fakeValuesService().resolve("science.tool", this);
    }

    public String quark() {
        return faker.fakeValuesService().resolve("science.particles.quarks", this);
    }

    public String leptons() {
        return faker.fakeValuesService().resolve("science.particles.leptons", this);
    }

    public String bosons() {
        return faker.fakeValuesService().resolve("science.particles.bosons", this);
    }

}
