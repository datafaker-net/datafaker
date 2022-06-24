package net.datafaker;

import net.datafaker.core.Faker;

public class BigBangTheory {

    private final Faker faker;

    protected BigBangTheory(Faker faker) {
        this.faker = faker;
    }

    /**
     * This method generates a random Big Bang Theory's character's name.
     *
     * @return a string of Big Bang Theory's character's name.
     */
    public String character() {
        return faker.fakeValuesService().resolve("big_bang_theory.characters", this, faker);
    }

    /**
     * This method generates a random Big Bang Theory's character's quote.
     *
     * @return a string of Big Bang Theory's character's quote.
     */
    public String quote() {
        return faker.fakeValuesService().resolve("big_bang_theory.quotes", this, faker);
    }
}
