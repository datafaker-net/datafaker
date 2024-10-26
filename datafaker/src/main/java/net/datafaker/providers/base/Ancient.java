package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Ancient extends AbstractProvider<BaseProviders> {

    protected Ancient(BaseProviders faker) {
        super(faker);
    }

    public String god() {
        return resolve("ancient.god");
    }

    public String primordial() {
        return resolve("ancient.primordial");
    }

    public String titan() {
        return resolve("ancient.titan");
    }

    public String hero() {
        return resolve("ancient.hero");
    }
}
