package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class DragonBall extends AbstractProvider<MovieProviders> {

    protected DragonBall(MovieProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("dragon_ball.characters");
    }
}
