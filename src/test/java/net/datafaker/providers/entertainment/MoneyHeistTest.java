package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class MoneyHeistTest extends EntertainmentFakerTest {

    private final MoneyHeist moneyHeist = getFaker().moneyHeist();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(moneyHeist::character, "money_heist.characters"),
            TestSpec.of(moneyHeist::heist, "money_heist.heists"),
            TestSpec.of(moneyHeist::quote, "money_heist.quotes")
        );
    }
}
