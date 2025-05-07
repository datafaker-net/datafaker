package net.datafaker.providers.sport;

import java.util.List;
import java.util.Collection;

class MartialArtTest extends SportFakerTest {

    private final MartialArt martialArt = getFaker().martialart();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(martialArt::name, "martial_art.name"),
            TestSpec.of(martialArt::origin, "martial_art.origin")
        );
    }
}
