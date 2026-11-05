package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class FamousLastWordsTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        FamousLastWords famousLastWords = faker.famousLastWords();
        return List.of(TestSpec.of(famousLastWords::lastWords, "famous_last_words.last_words", "^[A-Za-z- .,'!?-…]+$"));
    }
}
