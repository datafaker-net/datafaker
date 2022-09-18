package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class App extends AbstractProvider<IProviders> {

    protected App(BaseFaker faker) {
        super(faker);
    }

    public String name() {
        return resolve("app.name");
    }

    public String version() {
        return faker.numerify(faker.resolve("app.version"));
    }

    public String author() {
        return resolve("app.author");
    }
}
