package net.datafaker;

/**
 * @since 1.5.0
 */
public class FamousLastWords extends AbstractProvider {

    protected FamousLastWords(Faker faker) {
        super(faker);
    }

    /**
     * This method generates random famous last words.
     *
     * @return a string of last words.
     */
    public String lastWords() {
        return resolve("famous_last_words.last_words");
    }
}
