package net.datafaker;

public class Movie {

    private final Faker faker;

    protected Movie(Faker faker) {
        this.faker = faker;
    }

    /**
     * This method generates a random quote from a movie.
     *
     * @return a string of quote from a movie.
     */
    public String quote() {
        return faker.fakeValuesService().resolve("movie.quote", this, faker);
    }
}
