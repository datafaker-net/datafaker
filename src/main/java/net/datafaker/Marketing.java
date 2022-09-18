package net.datafaker;

/**
 * Generates marketing buzzwords.
 *
 * @since 1.2.0
 */
public class Marketing extends AbstractProvider<IProviders> {

    protected Marketing(BaseFaker faker) {
        super(faker);
    }

    public String buzzwords() {
        return resolve("marketing.buzzwords");
    }
}
