package net.datafaker;

/**
 * @since 0.8.0
 */
public class Artist extends AbstractProvider {

    protected Artist(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().fetchString("artist.names");
    }
}
