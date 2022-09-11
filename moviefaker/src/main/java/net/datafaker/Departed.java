package net.datafaker;

/**
 * @since 1.5.0
 */
public class Departed extends MovieProvider {

    protected Departed(MovieFaker faker) {
        super(faker);
    }

    /**
     * This method generates a random actor's name from The Departed.
     *
     * @return a string of actor's name from The Departed.
     */
    public String actor() {
        return faker.fakeValuesService().resolve("departed.actors", this);
    }

    /**
     * This method generates a random character's name from The Departed.
     *
     * @return a string of character's name from The Departed.
     */
    public String character() {
        return faker.fakeValuesService().resolve("departed.characters", this);
    }

    /**
     * This method generates a random quote from The Departed.
     *
     * @return a string of quote from The Departed.
     */
    public String quote() {
        return faker.fakeValuesService().resolve("departed.quotes", this);
    }
}
