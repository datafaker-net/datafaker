package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class HowIMetYourMother extends AbstractProvider<EntertainmentProviders> {

    protected HowIMetYourMother(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("how_i_met_your_mother.character");
    }

    public String catchPhrase() {
        return resolve("how_i_met_your_mother.catch_phrase");
    }

    public String highFive() {
        return resolve("how_i_met_your_mother.high_five");
    }

    public String quote() {
        return resolve("how_i_met_your_mother.quote");
    }
}
