package net.datafaker;

public class Departed {

    private final Faker faker;

    protected Departed(Faker faker) {
        this.faker = faker;
    }

    /**
     * This method generates a random actor's name from The Departed.
     *
     * @return a string of actor's name from The Departed.
     */
    public String actor() {
        return faker.fakeValuesService().resolve("departed.actors", this, faker);
    }

    /**
     * This method generates a random character's name from The Departed.
     *
     * @return a string of character's name from The Departed.
     */
    public String character() {
        return faker.fakeValuesService().resolve("departed.characters", this, faker);
    }

    /**
     * This method generates a random quote from The Departed.
     *
     * @return a string of quote from The Departed.
     */
    public String quote() {
        return faker.fakeValuesService().resolve("departed.quotes", this, faker);
    }
}
