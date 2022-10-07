package net.datafaker.providers.base;

/**
 * @since 1.6.0
 */
public class Chiquito extends AbstractProvider<BaseProviders> {

    protected Chiquito(BaseProviders faker) {
        super(faker);
    }

    public String expressions() {
        return resolve("chiquito.expressions");
    }

    public String terms() {
        return resolve("chiquito.terms");
    }

    public String sentences() {
        return resolve("chiquito.sentences");
    }

    public String jokes() {
        return resolve("chiquito.jokes");
    }

}
