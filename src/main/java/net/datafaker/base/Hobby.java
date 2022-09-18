package net.datafaker.base;

/**
 * @since 1.3.0
 */
public class Hobby extends AbstractProvider<IProviders> {

    protected Hobby(BaseFaker faker) {
        super(faker);
    }

    public String activity() {
        return resolve("hobby.activity");
    }

}
