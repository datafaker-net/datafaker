package net.datafaker;

/**
 * @since 1.5.0
 */
public class House {

    private final Faker faker;

    protected House(Faker faker) {
        this.faker = faker;
    }

    /**
     * This method generates a random name of furniture.
     *
     * @return a string of furniture.
     */
    public String furniture() {
        return faker.fakeValuesService().resolve("house.furniture", this, faker);
    }

    /**
     * This method generates a random name of a room in a house.
     *
     * @return a string of room.
     */
    public String room() {
        return faker.fakeValuesService().resolve("house.rooms", this, faker);
    }
}
