package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * How to Train Your Dragon is a 2010 American computer-animated action fantasy film loosely based on the 2003 book of the same name by Cressida Cowell.
 *
 * @since 1.8.0
 */
public class HowToTrainYourDragon extends AbstractProvider<EntertainmentProviders> {

    protected HowToTrainYourDragon(EntertainmentProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("how_to_train_your_dragon.characters");
    }

    public String dragons() {
        return resolve("how_to_train_your_dragon.dragons");
    }

    public String locations() {
        return resolve("how_to_train_your_dragon.locations");
    }

}
