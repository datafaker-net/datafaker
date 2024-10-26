package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


class StarWarsTest extends EntertainmentFakerTest {

    @Test
    void callSign() {
        assertThat(faker.starWars().callSign()).isNotEmpty().matches("\\w+\\s(Leader|\\d)$");
    }

    @Test
    void alternativeSpelling() {
        assertThat(faker.starWars().alternateCharacterSpelling()).isNotEmpty();
    }

    private final StarWars starWars = getFaker().starWars();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(starWars::character, "star_wars.characters"),
            TestSpec.of(starWars::droids, "star_wars.droids"),
            TestSpec.of(starWars::planets, "star_wars.planets"),
            TestSpec.of(starWars::species, "star_wars.species"),
            TestSpec.of(starWars::vehicles, "star_wars.vehicles"),
            TestSpec.of(starWars::wookieWords, "star_wars.wookiee_words")
        );
    }
}
