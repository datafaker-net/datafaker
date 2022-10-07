package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class App extends AbstractProvider<BaseProviders> {

    protected App(BaseProviders faker) {
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
