package net.datafaker;

import net.datafaker.core.Faker;

public class FamousLastWords {

    private final Faker faker;

    protected FamousLastWords(Faker faker) {
        this.faker = faker;
    }

    /**
     * This method generates random famous last words.
     *
     * @return a string of last words.
     */
    public String lastWords() {
        return faker.fakeValuesService().resolve("famous_last_words.last_words", this, faker);
    }
}
