package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class HowIMetYourMotherTest extends EntertainmentFakerTest {

    private final HowIMetYourMother howIMetYourMother = getFaker().howIMetYourMother();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(howIMetYourMother::catchPhrase, "how_i_met_your_mother.catch_phrase"),
            TestSpec.of(howIMetYourMother::character, "how_i_met_your_mother.character"),
            TestSpec.of(howIMetYourMother::highFive, "how_i_met_your_mother.high_five"),
            TestSpec.of(howIMetYourMother::quote, "how_i_met_your_mother.quote")
        );
    }
}
