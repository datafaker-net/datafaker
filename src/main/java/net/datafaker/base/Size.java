package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class Size extends AbstractProvider<IProviders> {

    protected Size(BaseFaker faker) {
        super(faker);
    }

    public String adjective() {
        return resolve("size.adjective");
    }

}
