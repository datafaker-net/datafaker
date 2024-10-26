package net.datafaker.providers.videogame;

import java.util.List;
import java.util.Collection;

public class ControlTest extends VideoGameFakerTest {

    private final Control control = getFaker().control();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(control::alteredItem, "control.altered_item"),
            TestSpec.of(control::alteredWorldEvent, "control.altered_world_event"),
            TestSpec.of(control::character, "control.character"),
            TestSpec.of(control::hiss, "control.hiss"),
            TestSpec.of(control::location, "control.location"),
            TestSpec.of(control::objectOfPower, "control.object_of_power"),
            TestSpec.of(control::quote, "control.quote"),
            TestSpec.of(control::theBoard, "control.the_board")
        );
    }
}
