package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

public class FamilyGuyTest extends EntertainmentFakerTest {

    private final FamilyGuy familyGuy = getFaker().familyGuy();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(familyGuy::character, "family_guy.character"),
            TestSpec.of(familyGuy::location, "family_guy.location"),
            TestSpec.of(familyGuy::quote, "family_guy.quote")
        );
    }
}
