package net.datafaker;

/**
 * @since 0.9.0
 */
public class EnglandFootBall extends AbstractProvider<SportFaker> {

    protected EnglandFootBall(final SportFaker faker) {
        super(faker);
    }

    public String league() {
        return faker.fakeValuesService().resolve("englandfootball.leagues", this);
    }

    public String team() {
        return faker.fakeValuesService().resolve("englandfootball.teams", this);
    }
}
