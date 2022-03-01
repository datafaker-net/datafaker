package net.datafaker;

/**
 * A class for generating random value of ResidentEvil series.
 *
 * @since 0.9.0
 */
public class ResidentEvil {
    private final Faker faker;

    protected ResidentEvil(Faker faker) {
        this.faker = faker;
    }

    /**
     * @return A random character string (like leon kennedy) of ResidentEvil series.
     */
    public String character() {
        return faker.fakeValuesService().resolve("games.resident_evil.characters", this, faker);
    }

    /**
     * @return A random biologicalAgent string of ResidentEvil series. This string may contains special characters.
     */
    public String biologicalAgent() {
        return faker.fakeValuesService().resolve("games.resident_evil.biological-agents", this, faker);
    }

    /**
     * @return A random equipment string of ResidentEvil series, which includes weapons and other items.
     */
    public String equipment() {
        return faker.fakeValuesService().resolve("games.resident_evil.equipments", this, faker);
    }

    /**
     * @return A random location string of ResidentEvil series.
     */
    public String location() {
        return faker.fakeValuesService().resolve("games.resident_evil.locations", this, faker);
    }

    /**
     * @return A random creature string of ResidentEvil series.
     */
    public String creature() {
        return faker.fakeValuesService().resolve("games.resident_evil.creatures", this, faker);
    }
}
