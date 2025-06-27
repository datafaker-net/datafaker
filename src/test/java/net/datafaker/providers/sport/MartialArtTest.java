package net.datafaker.providers.sport;

import java.util.List;
import java.util.Collection;

class MartialArtTest extends SportFakerTest {

    private final MartialArt martialArt = getFaker().martialArt();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(martialArt::name, "martial_art.name")
        );
    }
}
