package net.datafaker;

/**
 * issue for: https://github.com/datafaker-net/datafaker/issues/159
 * @author SickDawn
 */
public class DarkSoul {
    private final Faker faker;

    public DarkSoul(final Faker faker) {
        this.faker = faker;
    }

    public String stats(){
        return faker.fakeValuesService().resolve("dark_soul.stats", this, faker);
    }

    public String covenants(){
        return faker.fakeValuesService().resolve("dark_soul.covenants", this, faker);
    }

    public String classes(){
        return faker.fakeValuesService().resolve("dark_soul.classes", this, faker);
    }

    public String shield(){
        return faker.fakeValuesService().resolve("dark_soul.shield", this, faker);
    }

}
