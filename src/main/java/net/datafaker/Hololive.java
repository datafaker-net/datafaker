package net.datafaker;

/**
 * @since 1.5.0
 */
public class Hololive extends AbstractProvider {

    protected Hololive(Faker faker) {
        super(faker);
    }

    public String talent() {
        return faker.resolve("hololive.talent");
    }
}
