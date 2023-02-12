package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class PokemonTest extends EntertainmentFakerTest {

    private final Pokemon pokemon = getFaker().pokemon();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(pokemon::location, "games.pokemon.locations"),
            TestSpec.of(pokemon::move, "games.pokemon.moves"),
            TestSpec.of(pokemon::name, "games.pokemon.names"),
            TestSpec.of(pokemon::type, "games.pokemon.types")
        );
    }
}
