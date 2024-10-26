package net.datafaker.providers.base;

/**
 * The NATO phonetic alphabet is the most widely used radiotelephone spelling alphabet.
 *
 * @since 1.2.0
 */
public class NatoPhoneticAlphabet extends AbstractProvider<BaseProviders> {

    protected NatoPhoneticAlphabet(BaseProviders faker) {
        super(faker);
    }

    public String codeWord() {
        return resolve("nato_phonetic_alphabet.code_word");
    }

}
