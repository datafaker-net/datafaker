package net.datafaker;

/**
 * @since 0.9.0
 */
public class Babylon5 extends MovieProvider {

    protected Babylon5(MovieFaker faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("babylon5.characters");
    }

    public String quote() {
        return faker.resolve("babylon5.quotes");
    }
}
