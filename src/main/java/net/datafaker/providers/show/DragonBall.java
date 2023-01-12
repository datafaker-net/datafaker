package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class DragonBall extends AbstractProvider<ShowProviders> {

    protected DragonBall(ShowProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("dragon_ball.characters");
    }
}
