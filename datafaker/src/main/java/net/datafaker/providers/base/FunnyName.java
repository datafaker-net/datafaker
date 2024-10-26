package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class FunnyName extends AbstractProvider<BaseProviders> {

    protected FunnyName(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("funny_name.name");
    }
}
