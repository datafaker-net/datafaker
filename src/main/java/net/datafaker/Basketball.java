package net.datafaker;

/**
 * Generate random components of basketball game, e.g. teams, coaches, positions and players.
 *
 * @author unknown and irakatz
 * @since 0.8.0
 */
public class Basketball extends AbstractProvider {

    /**
     * Create a constructor for Basketball.
     *
     * @param faker The Faker instance for generating random, different kinds of disease, e.g. the internal disease.
     */
    protected Basketball(Faker faker) {
        super(faker);
    }

    /**
     * Generate random basketball teams
     *
     * @return Basketball teams
     */
    public String teams() {
        return faker.fakeValuesService().resolve("basketball.teams", this);
    }

    /**
     * Generate random coaches in basketball game
     *
     * @return Basketball coaches
     */
    public String coaches() {
        return faker.fakeValuesService().resolve("basketball.coaches", this);
    }

    /**
     * Generate random positions in basketball game
     *
     * @return Basketball positions
     */
    public String positions() {
        return faker.fakeValuesService().resolve("basketball.positions", this);
    }

    /**
     * Generate random basketball players
     *
     * @return Basketball players
     */
    public String players() {
        return faker.fakeValuesService().resolve("basketball.players", this);
    }
}
