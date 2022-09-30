package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * @since 1.7.0
 */
public class MoneyHeist extends AbstractProvider<MovieProviders> {

    protected MoneyHeist(MovieProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("money_heist.characters");
    }

    public String heist() {
        return resolve("money_heist.heists");
    }

    public String quote() {
        return resolve("money_heist.quotes");
    }
}
