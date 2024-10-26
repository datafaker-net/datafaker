package net.datafaker.providers.base;

/**
 * Providers related to Large Language Models (LLMs)
 *
 * @since 2.3.0
 */
public class LargeLanguageModel extends AbstractProvider<BaseProviders> {

    protected LargeLanguageModel(BaseProviders faker) {
        super(faker);
    }

    public String textModel() {
        return resolve("largelanguagemodel.text_models");
    }

    public String embeddingModel() {
        return resolve("largelanguagemodel.embeddings");
    }

    public String tokenizer() {
        return resolve("largelanguagemodel.tokenizers");
    }

}
