package net.datafaker;

/**
 * @since 1.5.0
 */
public class GreekPhilosopher extends AbstractProvider {

    protected GreekPhilosopher(Faker faker) {
        super(faker);
    }

    /**
     * This method generates a random greek philosopher's name.
     *
     * @return a string of greek philosopher's name.
     */
    public String name() {
        return faker.fakeValuesService().resolve("greek_philosophers.names", this);
    }

    /**
     * This method generates a random greek philosopher's quote.
     *
     * @return a string of greek philosopher's quote.
     */
    public String quote() {
        return faker.fakeValuesService().resolve("greek_philosophers.quotes", this);
    }
}
