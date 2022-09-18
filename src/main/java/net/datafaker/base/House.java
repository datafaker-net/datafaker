package net.datafaker.base;

/**
 * @since 1.5.0
 */
public class House extends AbstractProvider<IProviders> {

    protected House(BaseFaker faker) {
        super(faker);
    }

    /**
     * This method generates a random name of furniture.
     *
     * @return a string of furniture.
     */
    public String furniture() {
        return resolve("house.furniture");
    }

    /**
     * This method generates a random name of a room in a house.
     *
     * @return a string of room.
     */
    public String room() {
        return resolve("house.rooms");
    }
}
