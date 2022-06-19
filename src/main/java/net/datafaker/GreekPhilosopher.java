package net.datafaker;

import net.datafaker.core.Faker;

public class GreekPhilosopher {

    private final Faker faker;

    protected GreekPhilosopher(Faker faker) {
        this.faker = faker;
    }

    /**
     * This method generates a random greek philosopher's name.
     *
     * @return a string of greek philosopher's name.
     */
    public String name() {
        return faker.fakeValuesService().resolve("greek_philosophers.names", this, faker);
    }

    /**
     * This method generates a random greek philosopher's quote.
     *
     * @return a string of greek philosopher's quote.
     */
    public String quote() {
        return faker.fakeValuesService().resolve("greek_philosophers.quotes", this, faker);
    }
}
