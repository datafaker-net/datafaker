package net.datafaker;

/**
 * @since 0.8.0
 */
public class ChuckNorris extends MovieProvider {

    protected ChuckNorris(MovieFaker faker) {
        super(faker);
    }

    public String fact() {
        return faker.fakeValuesService().resolve("chuck_norris.fact", this);
    }
}
