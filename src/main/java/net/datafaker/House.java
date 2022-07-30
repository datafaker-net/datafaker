package net.datafaker;

/**
 * @since 1.5.0
 */
public class House extends AbstractProvider {

    protected House(Faker faker) {
        super(faker);
    }

    /**
     * This method generates a random name of furniture.
     *
     * @return a string of furniture.
     */
    public String furniture() {
        return faker.fakeValuesService().resolve("house.furniture", this);
    }

    /**
     * This method generates a random name of a room in a house.
     *
     * @return a string of room.
     */
    public String room() {
        return faker.fakeValuesService().resolve("house.rooms", this);
    }
}
