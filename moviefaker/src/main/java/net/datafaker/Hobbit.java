package net.datafaker;

/**
 * @since 0.8.0
 */
public class Hobbit extends MovieProvider {

    protected Hobbit(MovieFaker faker) {
        super(faker);
    }

    public String character() {
        return faker.fakeValuesService().resolve("hobbit.character", this);
    }

    public String thorinsCompany() {
        return faker.fakeValuesService().resolve("hobbit.thorins_company", this);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("hobbit.quote", this);
    }

    public String location() {
        return faker.fakeValuesService().resolve("hobbit.location", this);
    }
}
