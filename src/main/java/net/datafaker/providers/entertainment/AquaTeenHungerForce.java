package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class AquaTeenHungerForce extends AbstractProvider<EntertainmentProviders> {

    protected AquaTeenHungerForce(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("aqua_teen_hunger_force.character");
    }

}
