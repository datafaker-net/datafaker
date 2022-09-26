package net.datafaker.base;

/**
 * @since 1.3.0
 */
public class Hobby extends AbstractProvider<BaseProviders> {

    protected Hobby(BaseProviders faker) {
        super(faker);
    }

    public String activity() {
        return resolve("hobby.activity");
    }

}
