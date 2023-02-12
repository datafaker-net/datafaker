package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class CowboyBebopTest extends EntertainmentFakerTest {

    private final CowboyBebop cowboyBebop = getFaker().cowboyBebop();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(cowboyBebop::character, "cowboy_bebop.character"),
            TestSpec.of(cowboyBebop::episode, "cowboy_bebop.episode"),
            TestSpec.of(cowboyBebop::song, "cowboy_bebop.song"),
            TestSpec.of(cowboyBebop::quote, "cowboy_bebop.quote")
        );
    }
}
