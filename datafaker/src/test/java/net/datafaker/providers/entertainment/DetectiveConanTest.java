package net.datafaker.providers.entertainment;


import java.util.List;
import java.util.Collection;

class DetectiveConanTest extends EntertainmentFakerTest {

    private final DetectiveConan detectiveConan = getFaker().detectiveConan();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(detectiveConan::characters, "detective_conan.characters"),
            TestSpec.of(detectiveConan::gadgets, "detective_conan.gadgets"),
            TestSpec.of(detectiveConan::vehicles, "detective_conan.vehicles")
        );
    }
}
