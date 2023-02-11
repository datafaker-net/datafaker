package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

class TheRoomTest extends EntertainmentFakerTest {

    private final TheRoom theRoom = getFaker().theRoom();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(theRoom::actors, "the_room.actors"),
            TestSpec.of(theRoom::characters, "the_room.characters"),
            TestSpec.of(theRoom::locations, "the_room.locations"),
            TestSpec.of(theRoom::quotes, "the_room.quotes")
        );
    }
}
