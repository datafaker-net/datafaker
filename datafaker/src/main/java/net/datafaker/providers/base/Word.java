package net.datafaker.providers.base;


/**
 * @since 2.4.0
 */
public class Word extends AbstractProvider<BaseProviders> {

    protected Word(BaseProviders faker) {
        super(faker);
    }

    public String adjective() {
        return resolve("words.adjective");
    }

    public String adverb() {
        return resolve("words.adverb");
    }

    public String conjunction() {
        return resolve("words.conjunction");
    }

    public String interjection() {
        return resolve("words.interjection");
    }

    public String noun() {
        return resolve("words.noun");
    }

    public String preposition() {
        return resolve("words.preposition");
    }

    public String verb() {
        return resolve("words.verb");
    }

}
