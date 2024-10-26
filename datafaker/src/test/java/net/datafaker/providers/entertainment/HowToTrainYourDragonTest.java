package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class HowToTrainYourDragonTest extends EntertainmentFakerTest {

    private final HowToTrainYourDragon howToTrainYourDragon = getFaker().howToTrainYourDragon();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(howToTrainYourDragon::characters, "how_to_train_your_dragon.characters"),
            TestSpec.of(howToTrainYourDragon::dragons, "how_to_train_your_dragon.dragons"),
            TestSpec.of(howToTrainYourDragon::locations, "how_to_train_your_dragon.locations")
        );
    }
}

