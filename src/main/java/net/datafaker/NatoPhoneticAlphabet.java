package net.datafaker;

/**
 * The NATO phonetic alphabet is the most widely used radiotelephone spelling alphabet.
 *
 * @since 1.2.0
 */
public class NatoPhoneticAlphabet extends AbstractProvider<IProviders> {

    protected NatoPhoneticAlphabet(BaseFaker faker) {
        super(faker);
    }

    public String codeWord() {
        return resolve("nato_phonetic_alphabet.code_word");
    }

}
