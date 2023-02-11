package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class FuturamaTest extends EntertainmentFakerTest {

    private final Futurama futurama = getFaker().futurama();
    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(futurama::character, "futurama.characters"),
            TestSpec.of(futurama::location, "futurama.locations"),
            TestSpec.of(futurama::hermesCatchPhrase, "futurama.hermes_catchphrases"),
            TestSpec.of(futurama::quote, "futurama.quotes")
        );
    }
}
