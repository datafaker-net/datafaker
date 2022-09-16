package net.datafaker;

/**
 * @since 0.9.0
 */
public class EnglandFootBall extends AbstractProvider {

    protected EnglandFootBall(final Faker faker) {
        super(faker);
    }

    public String league() {
        return resolve("englandfootball.leagues");
    }

    public String team() {
        return resolve("englandfootball.teams");
    }
}
