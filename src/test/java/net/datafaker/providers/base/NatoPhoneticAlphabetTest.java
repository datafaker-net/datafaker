package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class NatoPhoneticAlphabetTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.natoPhoneticAlphabet().codeWord(), "nato_phonetic_alphabet.code_word"));
    }

}
