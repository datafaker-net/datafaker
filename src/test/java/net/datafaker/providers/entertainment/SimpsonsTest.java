package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;


public class SimpsonsTest extends EntertainmentFakerTest {

    private final Simpsons simpsons = getFaker().simpsons();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(simpsons::character, "simpsons.characters"),
            TestSpec.of(simpsons::location, "simpsons.locations"),
            TestSpec.of(simpsons::quote, "simpsons.quotes")
        );
    }
}
