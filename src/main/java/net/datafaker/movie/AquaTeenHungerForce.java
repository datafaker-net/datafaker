package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class AquaTeenHungerForce extends AbstractProvider<MovieProviders> {

    protected AquaTeenHungerForce(MovieProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("aqua_teen_hunger_force.character");
    }

}
