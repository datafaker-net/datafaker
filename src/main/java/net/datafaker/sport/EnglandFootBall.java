package net.datafaker.sport;

import net.datafaker.AbstractProvider;

/**
 * @since 0.9.0
 */
public class EnglandFootBall extends AbstractProvider<SportProviders> {

    protected EnglandFootBall(final SportProviders faker) {
        super(faker);
    }

    public String league() {
        return resolve("englandfootball.leagues");
    }

    public String team() {
        return resolve("englandfootball.teams");
    }
}
