package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Size extends AbstractProvider<BaseProviders> {

    protected Size(BaseProviders faker) {
        super(faker);
    }

    public String adjective() {
        return resolve("size.adjective");
    }

}
