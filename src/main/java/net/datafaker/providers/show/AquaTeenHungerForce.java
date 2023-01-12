package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class AquaTeenHungerForce extends AbstractProvider<ShowProviders> {

    protected AquaTeenHungerForce(ShowProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("aqua_teen_hunger_force.character");
    }

}
