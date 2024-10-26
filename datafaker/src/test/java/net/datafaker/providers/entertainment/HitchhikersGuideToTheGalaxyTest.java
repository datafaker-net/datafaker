package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;


class HitchhikersGuideToTheGalaxyTest extends EntertainmentFakerTest {

    private final HitchhikersGuideToTheGalaxy hitchhikersGuideToTheGalaxy = getFaker().hitchhikersGuideToTheGalaxy();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(hitchhikersGuideToTheGalaxy::character, "hitchhikers_guide_to_the_galaxy.characters"),
            TestSpec.of(hitchhikersGuideToTheGalaxy::location, "hitchhikers_guide_to_the_galaxy.locations"),
            TestSpec.of(hitchhikersGuideToTheGalaxy::marvinQuote, "hitchhikers_guide_to_the_galaxy.marvin_quote"),
            TestSpec.of(hitchhikersGuideToTheGalaxy::planet, "hitchhikers_guide_to_the_galaxy.planets"),
            TestSpec.of(hitchhikersGuideToTheGalaxy::species, "hitchhikers_guide_to_the_galaxy.species"),
            TestSpec.of(hitchhikersGuideToTheGalaxy::starship, "hitchhikers_guide_to_the_galaxy.starships"),
            TestSpec.of(hitchhikersGuideToTheGalaxy::quote, "hitchhikers_guide_to_the_galaxy.quotes")
        );
    }
}
