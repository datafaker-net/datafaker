package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class DragonBall extends AbstractProvider<EntertainmentProviders> {

    protected DragonBall(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("dragon_ball.characters");
    }
}
