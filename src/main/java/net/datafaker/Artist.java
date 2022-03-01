package net.datafaker;

/**
 * @since 0.8.0
 */
public class Artist {

    private final Faker faker;

    protected Artist(Faker faker) {
        this.faker = faker;
    }

    public String name() {
        return faker.fakeValuesService().fetchString("artist.names");
    }
}
