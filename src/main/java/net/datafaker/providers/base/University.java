package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class University extends AbstractProvider<BaseProviders> {

    protected University(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("university.name");
    }

    public String degree() {
        return resolve("university.degree");
    }

    public String prefix() {
        return resolve("university.prefix");
    }

    public String suffix() {
        return resolve("university.suffix");
    }
}
