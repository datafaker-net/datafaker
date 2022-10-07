package net.datafaker.providers.base;

/**
 * @since 1.5.0
 */
public class FamousLastWords extends AbstractProvider<BaseProviders> {

    protected FamousLastWords(BaseProviders faker) {
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
