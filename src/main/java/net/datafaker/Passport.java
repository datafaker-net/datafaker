package net.datafaker;

/**
 * @since 0.9.0
 */
public class Passport extends AbstractProvider {

    protected Passport(Faker faker) {
        super(faker);
    }

    public String valid() {
        return faker.regexify(faker.resolve("passport.valid"));
    }
}
