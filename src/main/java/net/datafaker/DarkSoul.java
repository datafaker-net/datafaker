package net.datafaker;

/**
 * issue for: <a href="https://github.com/datafaker-net/datafaker/issues/159">159</a>
 *
 * @since 1.5.0
 * @author SickDawn
 */
public class DarkSoul extends AbstractProvider {

    public DarkSoul(final Faker faker) {
        super(faker);
    }

    public String stats() {
        return faker.fakeValuesService().resolve("dark_soul.stats", this);
    }

    public String covenants() {
        return faker.fakeValuesService().resolve("dark_soul.covenants", this);
    }

    public String classes() {
        return faker.fakeValuesService().resolve("dark_soul.classes", this);
    }

    public String shield() {
        return faker.fakeValuesService().resolve("dark_soul.shield", this);
    }

}
