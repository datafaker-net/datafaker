package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class HitchhikersGuideToTheGalaxy extends AbstractProvider<EntertainmentProviders> {

    protected HitchhikersGuideToTheGalaxy(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("hitchhikers_guide_to_the_galaxy.characters");
    }

    public String location() {
        return resolve("hitchhikers_guide_to_the_galaxy.locations");
    }

    public String marvinQuote() {
        return resolve("hitchhikers_guide_to_the_galaxy.marvin_quote");
    }

    public String planet() {
        return resolve("hitchhikers_guide_to_the_galaxy.planets");
    }

    public String quote() {
        return resolve("hitchhikers_guide_to_the_galaxy.quotes");
    }

    public String species() {
        return resolve("hitchhikers_guide_to_the_galaxy.species");
    }

    public String starship() {
        return resolve("hitchhikers_guide_to_the_galaxy.starships");
    }
}
