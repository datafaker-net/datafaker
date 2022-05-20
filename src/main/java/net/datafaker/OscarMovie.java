package net.datafaker;

/**
 * The Academy Awards, popularly known as the Oscars, are awards for artistic and technical merit in the film industry.
 *
 * @author ak-maker
 * @since 1.4.0
 */
public class OscarMovie {

    private final Faker faker;
    private final String year;
    private final String choice;

    private final String str;

    /**
     * This is the constructor initialize faker and two other
     * variable for random generation.
     *
     * @param faker faker The Faker instance for generating random names of things.
     */
    protected OscarMovie(final Faker faker) {
        this.faker = faker;
        this.year = this.faker.resolve("oscar_movie.year.years");
        this.choice = this.faker.resolve("oscar_movie.year.choice");
        this.str = "oscar_movie.".concat(year).concat(".").concat(choice);
    }

    /**
     * @return year
     */
    public String getYear() {
        return year;
    }

    /**
     * @return choice
     */
    public String getChoice() {
        return choice;
    }

    /**
     * This method generates random actor
     *
     * @return random actor
     */
    public String actor() {
        return faker.resolve(str.concat(".actor"));
    }

    /**
     * This method generates a random movie name
     *
     * @return random movieName
     */
    public String movieName() {
        return faker.resolve(str.concat(".movieName"));
    }

    /**
     * This method generates a random quote
     *
     * @return random quote
     */
    public String quote() {
        return faker.resolve(str.concat(".quote"));
    }

    /**
     * This method generates a random character
     *
     * @return random character
     */
    public String character() {
        return faker.resolve(str.concat(".character"));
    }

    /**
     * This method generates a random release date
     *
     * @return random releaseDate
     */
    public String releaseDate() {
        return faker.resolve(str.concat(".releaseDate"));
    }
}
