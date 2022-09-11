package net.datafaker;

/**
 * @since 0.8.0
 */
public class DragonBall extends MovieProvider {

    protected DragonBall(MovieFaker faker) {
        super(faker);
    }

    public String character() {
        return faker.fakeValuesService().resolve("dragon_ball.characters", this);
    }
}
