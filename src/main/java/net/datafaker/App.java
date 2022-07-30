package net.datafaker;

/**
 * @since 0.8.0
 */
public class App extends AbstractProvider {

    protected App(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("app.name", this);
    }

    public String version() {
        return faker.numerify(faker.fakeValuesService().resolve("app.version", this));
    }

    public String author() {
        return faker.fakeValuesService().resolve("app.author", this);
    }
}
