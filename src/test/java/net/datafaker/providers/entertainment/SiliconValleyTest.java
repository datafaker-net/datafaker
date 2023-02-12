package net.datafaker.providers.entertainment;

import java.util.List;
import java.util.Collection;

public class SiliconValleyTest extends EntertainmentFakerTest {

    private final SiliconValley siliconValley = getFaker().siliconValley();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(siliconValley::app, "silicon_valley.apps"),
            TestSpec.of(siliconValley::character, "silicon_valley.characters"),
            TestSpec.of(siliconValley::company, "silicon_valley.companies"),
            TestSpec.of(siliconValley::email, "silicon_valley.email"),
            TestSpec.of(siliconValley::invention, "silicon_valley.inventions"),
            TestSpec.of(siliconValley::motto, "silicon_valley.mottos"),
            TestSpec.of(siliconValley::quote, "silicon_valley.quotes"),
            TestSpec.of(siliconValley::url, "silicon_valley.urls")
        );
    }
}
