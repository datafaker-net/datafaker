package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * RuPaul's Drag Race is a reality competition series produced by World of Wonder for the Logo TV Network.
 *
 * @since 1.0.0
 */
public class RuPaulDragRace extends AbstractProvider<MovieProviders> {

    protected RuPaulDragRace(MovieProviders faker) {
        super(faker);
    }

    public String queen() {
        return resolve("rupaul.queens");
    }

    public String quote() {
        return resolve("rupaul.quotes");
    }
}
