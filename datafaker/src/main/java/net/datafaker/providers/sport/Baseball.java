package net.datafaker.providers.sport;

import net.datafaker.providers.base.AbstractProvider;

/**

 * Generate random components of baseball game, e.g. teams, coaches, positions and players.
 *
 * @since 1.7.0
 */
public class Baseball extends AbstractProvider<SportProviders> {

    /**
     Baseball
     * Create a constructor for Baseball.
     *
     * @param faker The Faker instance for generating random, different kinds of disease, e.g. the internal disease.
     */
    protected Baseball(SportProviders faker) {
        super(faker);
    }

    /**
     * Generate random Baseball teams
     *
     * @return Baseball teams
     */
    public String teams() {
        return resolve("baseball.teams");
    }

    /**
     * Generate random coaches in baseball game
     *
     * @return Baseball coaches
     */
    public String coaches() {
        return resolve("baseball.coaches");
    }

    /**
     * Generate random positions in baseball game
     *
     * @return Baseball positions
     */
    public String positions() {
        return resolve("baseball.positions");
    }

    /**
     * Generate random baseball players
     *
     * @return Baseball players
     */
    public String players() {
        return resolve("baseball.players");
    }
}
