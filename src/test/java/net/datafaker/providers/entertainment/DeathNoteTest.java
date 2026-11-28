package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;


public class DeathNoteTest extends EntertainmentFakerTest {

    private final DeathNote deathNote = getFaker().deathNote();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(deathNote::character, "death_note.characters"),
            TestSpec.of(deathNote::location, "death_note.locations"),
            TestSpec.of(deathNote::quote, "death_note.quotes"),
            TestSpec.of(deathNote::shinigami, "death_note.shinigami")
        );
    }
}
