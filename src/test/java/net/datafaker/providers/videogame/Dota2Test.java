package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Dota2Test extends VideoGameFakerTest {
    private final Dota2 dota2 = getFaker().dota2();

    // All heroes that are currently contained within the provider
    private final Set<String> availableHeroes = Set.of(
        "abaddon",
        "alchemist",
        "axe",
        "beastmaster",
        "brewmaster",
        "bristleback",
        "centaur",
        "chaos_knight",
        "clockwerk",
        "dawn_breaker",
        "doom",
        "dragon_knight",
        "earth_spirit",
        "earthshaker",
        "elder_titan",
        "huskar",
        "io",
        "kunkka",
        "legion_commander",
        "lifestealer",
        "lycan",
        "magnus",
        "meepo",
        "night_stalker",
        "omniknight",
        "primal_beast",
        "phoenix",
        "pudge",
        "sand_king",
        "slardar",
        "spirit_breaker",
        "sven",
        "tidehunter",
        "timbersaw",
        "tiny",
        "treant_protector",
        "tusk",
        "underlord",
        "undying",
        "wraith_king",
        "tinker",
        "muerta",
        "arc_warden",
        "batrider",
        "visage",
        "broodmother",
        "techies",
        "viper",
        "rubick",
        "morphling",
        "outworld_destroyer",
        "puck",
        "chen",
        "oracle",
        "warlock",
        "bane",
        "spectre",
        "clinkz",
        "shadow_demon",
        "pugna",
        "marci",
        "enchantress",
        "jakiro",
        "weaver",
        "naga_siren"
    );

    @Test
    void dota2HeroQuote() {
        List<String> availableHeroList = new ArrayList<>(availableHeroes);

        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(availableHeroList.size());

            String randomHero = availableHeroList.get(randomIndex);

            String randomHeroQuote = dota2.heroQuote(randomHero);

            assertThat(randomHeroQuote).isNotEmpty();
        }
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(dota2::faction, "games.dota2.faction"),
            TestSpec.of(dota2::rank, "games.dota2.rank"),
            TestSpec.of(dota2::attribute, "games.dota2.attribute"),
            TestSpec.of(dota2::building, "games.dota2.building"),
            TestSpec.of(dota2::hero, "games.dota2.hero"),
            TestSpec.of(dota2::item, "games.dota2.item"),
            TestSpec.of(dota2::team, "games.dota2.team"),
            TestSpec.of(dota2::player, "games.dota2.player")
        );
    }
}
