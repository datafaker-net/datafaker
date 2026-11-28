package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;


public class BlueyTest extends EntertainmentFakerTest {

    private final Bluey bluey = getFaker().bluey();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(bluey::character, "bluey.characters"),
            TestSpec.of(bluey::location, "bluey.locations"),
            TestSpec.of(bluey::quote, "bluey.quotes")
        );
    }
}
