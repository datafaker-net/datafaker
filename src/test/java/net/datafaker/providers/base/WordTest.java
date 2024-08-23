package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class WordTest extends BaseFakerTest<BaseFaker> {

    private final Word word = faker.word();

    @Override
    protected Collection<BaseFakerTest.TestSpec> providerListTest() {
        return List.of(
            BaseFakerTest.TestSpec.of(word::adjective, "words.adjective"),
            BaseFakerTest.TestSpec.of(word::adverb, "words.adverb"),
            BaseFakerTest.TestSpec.of(word::conjunction, "words.conjunction"),
            BaseFakerTest.TestSpec.of(word::interjection, "words.interjection"),
            BaseFakerTest.TestSpec.of(word::noun, "words.noun"),
            BaseFakerTest.TestSpec.of(word::preposition, "words.preposition"),
            BaseFakerTest.TestSpec.of(word::verb, "words.verb"));
    }


}
