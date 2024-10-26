package net.datafaker.providers.base;

/**
 * Generates marketing buzzwords.
 *
 * @since 1.2.0
 */
public class Marketing extends AbstractProvider<BaseProviders> {

    protected Marketing(BaseProviders faker) {
        super(faker);
    }

    public String buzzwords() {
        return resolve("marketing.buzzwords");
    }
}
