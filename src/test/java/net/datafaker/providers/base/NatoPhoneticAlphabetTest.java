package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class NatoPhoneticAlphabetTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        NatoPhoneticAlphabet natoAlpha = faker.natoPhoneticAlphabet();
        return List.of(TestSpec.of(natoAlpha::codeWord, "nato_phonetic_alphabet.code_word"));
    }

}
