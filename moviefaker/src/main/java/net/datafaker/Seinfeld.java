package net.datafaker;

/**
 * Seinfeld is an American sitcom television series created by Larry David and Jerry Seinfeld.
 *
 * @since 1.4.0
 */
public class Seinfeld extends MovieProvider {

    protected Seinfeld(MovieFaker faker) {
        super(faker);
    }

    public String character() {
        return faker.fakeValuesService().resolve("seinfeld.character", this);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("seinfeld.quote", this);
    }

    public String business() {
        return faker.fakeValuesService().resolve("seinfeld.business", this);
    }

}
