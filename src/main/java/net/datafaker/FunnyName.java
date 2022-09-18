package net.datafaker;

/**
 * @since 0.8.0
 */
public class FunnyName extends AbstractProvider<IProviders> {

    protected FunnyName(BaseFaker faker) {
        super(faker);
    }

    public String name() {
        return resolve("funny_name.name");
    }
}
