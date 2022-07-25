package net.datafaker;

/**
 * @since 1.5.0
 */
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
