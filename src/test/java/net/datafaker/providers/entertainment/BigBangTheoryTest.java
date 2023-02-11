package net.datafaker.providers.entertainment;

import java.util.Arrays;
import java.util.Collection;

class BigBangTheoryTest extends EntertainmentFakerTest {

    private final BigBangTheory bigBangTheory = getFaker().bigBangTheory();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(bigBangTheory::character, "big_bang_theory.characters"),
            TestSpec.of(bigBangTheory::quote, "big_bang_theory.quotes"));
    }
}
