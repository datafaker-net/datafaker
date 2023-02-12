package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class HarryPotterTest extends EntertainmentFakerTest {

    private final HarryPotter harryPotter = getFaker().harryPotter();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(harryPotter::book, "harry_potter.books"),
            TestSpec.of(harryPotter::character, "harry_potter.characters"),
            TestSpec.of(harryPotter::house, "harry_potter.houses"),
            TestSpec.of(harryPotter::location, "harry_potter.locations"),
            TestSpec.of(harryPotter::quote, "harry_potter.quotes"),
            TestSpec.of(harryPotter::spell, "harry_potter.spells")
        );
    }
}
