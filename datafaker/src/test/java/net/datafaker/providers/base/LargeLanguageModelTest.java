package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

class LargeLanguageModelTest extends BaseFakerTest<BaseFaker> {

    private final LargeLanguageModel llm = faker.largeLanguageModel();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(llm::textModel, "largelanguagemodel.text_models"),
            TestSpec.of(llm::embeddingModel, "largelanguagemodel.embeddings"),
            TestSpec.of(llm::tokenizer, "largelanguagemodel.tokenizers"));
    }
}
