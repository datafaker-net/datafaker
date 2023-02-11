package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class NatoPhoneticAlphabetTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.natoPhoneticAlphabet().codeWord(), "nato_phonetic_alphabet.code_word"));
    }

}
