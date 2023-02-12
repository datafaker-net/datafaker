package net.datafaker.providers.sport;

import java.util.List;
import java.util.Collection;

class Formula1Test extends SportFakerTest {

    private final Formula1 formula1 = getFaker().formula1();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(formula1::circuit, "formula1.circuit"),
            TestSpec.of(formula1::driver, "formula1.driver"),
            TestSpec.of(formula1::grandPrix, "formula1.grand_prix"),
            TestSpec.of(formula1::team, "formula1.team")
        );
    }
}
