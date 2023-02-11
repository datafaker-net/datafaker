package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class RickAndMortyTest extends EntertainmentFakerTest {

    private final RickAndMorty rickAndMorty = getFaker().rickAndMorty();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(rickAndMorty::character, "rick_and_morty.characters"),
            TestSpec.of(rickAndMorty::location, "rick_and_morty.locations"),
            TestSpec.of(rickAndMorty::quote, "rick_and_morty.quotes")
        );
    }
}
