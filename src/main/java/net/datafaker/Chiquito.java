package net.datafaker;

/**
 * @since 1.6.0
 */
public class Chiquito extends AbstractProvider<IProviders> {

    protected Chiquito(BaseFaker faker) {
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
