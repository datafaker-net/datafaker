package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class University extends AbstractProvider<BaseProviders> {

    protected University(BaseFaker faker) {
        super(faker);
    }

    public String name() {
        return resolve("university.name");
    }

    public String prefix() {
        return resolve("university.prefix");
    }

    public String suffix() {
        return resolve("university.suffix");
    }
}
