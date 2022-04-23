package net.datafaker;

/**
 * Seinfeld is an American sitcom television series created by Larry David and Jerry Seinfeld.
 *
 * @since 1.4.0
 */
public class Seinfeld {

    private final Faker faker;

    protected Seinfeld(Faker faker) {
        this.faker = faker;
    }

    public String character() {
        return faker.fakeValuesService().resolve("seinfeld.character", this, faker);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("seinfeld.quote", this, faker);
    }

    public String business() {
        return faker.fakeValuesService().resolve("seinfeld.business", this, faker);
    }

}
