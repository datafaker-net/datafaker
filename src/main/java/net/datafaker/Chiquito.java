package net.datafaker;

/**
 * @since 1.6.0
 */
public class Chiquito extends AbstractProvider {

    protected Chiquito(Faker faker) {
        super(faker);
    }

    public String expressions() {
        return faker.fakeValuesService().resolve("chiquito.expressions", this);
    }

    public String terms() {
        return faker.fakeValuesService().resolve("chiquito.terms", this);
    }

    public String sentences() {
        return faker.fakeValuesService().resolve("chiquito.sentences", this);
    }

    public String jokes() {
        return faker.fakeValuesService().resolve("chiquito.jokes", this);
    }

}
