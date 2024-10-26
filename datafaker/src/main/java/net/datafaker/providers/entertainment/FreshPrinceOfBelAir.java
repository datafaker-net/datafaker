package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * The Fresh Prince of Bel-Air is an American television sitcom created by Andy and Susan Borowitz for NBC.
 *
 * @since 1.7.0
 */
public class FreshPrinceOfBelAir extends AbstractProvider<EntertainmentProviders> {

    protected FreshPrinceOfBelAir(EntertainmentProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("fresh_prince_of_bel_air.characters");
    }

    public String celebrities() {
        return resolve("fresh_prince_of_bel_air.celebrities");
    }

    public String quotes() {
        return resolve("fresh_prince_of_bel_air.quotes");
    }

}
