package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class WitcherTest extends EntertainmentFakerTest {

    private final Witcher witcher = getFaker().witcher();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(witcher::book, "games.witcher.books"),
            TestSpec.of(witcher::character, "games.witcher.characters"),
            TestSpec.of(witcher::location, "games.witcher.locations"),
            TestSpec.of(witcher::monster, "games.witcher.monsters"),
            TestSpec.of(witcher::potion, "games.witcher.potions"),
            TestSpec.of(witcher::quote, "games.witcher.quotes"),
            TestSpec.of(witcher::sign, "games.witcher.signs"),
            TestSpec.of(witcher::school, "games.witcher.schools"),
            TestSpec.of(witcher::witcher, "games.witcher.witchers")
        );
    }
}
