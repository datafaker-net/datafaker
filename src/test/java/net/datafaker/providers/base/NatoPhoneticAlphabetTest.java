package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class NatoPhoneticAlphabetTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        NatoPhoneticAlphabet natoAlpha = faker.natoPhoneticAlphabet();
        return Arrays.asList(TestSpec.of(natoAlpha::codeWord, "nato_phonetic_alphabet.code_word"));
    }

}
