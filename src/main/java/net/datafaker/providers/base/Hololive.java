package net.datafaker.providers.base;

/**
 * @since 1.5.0
 */
public class Hololive extends AbstractProvider<BaseProviders> {

    protected Hololive(BaseProviders faker) {
        super(faker);
    }

    public String talent() {
        return resolve("hololive.talent");
    }
}
