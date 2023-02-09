package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class TheKingkillerChronicleTest extends EntertainmentFakerTest {

    private final TheKingkillerChronicle theKingkillerChronicle = getFaker().theKingkillerChronicle();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(theKingkillerChronicle::book, "the_kingkiller_chronicle.books"),
            TestSpec.of(theKingkillerChronicle::character, "the_kingkiller_chronicle.characters"),
            TestSpec.of(theKingkillerChronicle::creature, "the_kingkiller_chronicle.creatures"),
            TestSpec.of(theKingkillerChronicle::location, "the_kingkiller_chronicle.locations")
        );
    }
}
