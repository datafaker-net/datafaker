package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

public class DoraemonTest extends EntertainmentFakerTest {

    private final Doraemon doraemon = getFaker().doraemon();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(doraemon::character, "doraemon.characters"),
            TestSpec.of(doraemon::gadget, "doraemon.gadgets"),
            TestSpec.of(doraemon::location, "doraemon.locations")
        );
    }
}
