package net.datafaker;

/**
 * @since 0.8.0
 */
public class Kaamelott extends MovieProvider {

    protected Kaamelott(MovieFaker faker) {
        super(faker);
    }

    public String character() {
        return faker.fakeValuesService().resolve("kaamelott.characters", this);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("kaamelott.quotes", this);
    }
}
