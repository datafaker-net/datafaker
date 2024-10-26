package net.datafaker.providers.base;

/**
 * @since 0.9.0
 */
public class Passport extends AbstractProvider<BaseProviders> {

    protected Passport(BaseProviders faker) {
        super(faker);
    }

    public String valid() {
        return faker.regexify(faker.resolve("passport.valid"));
    }
}
