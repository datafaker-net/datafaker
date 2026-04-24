package net.datafaker.providers.base;
import net.datafaker.providers.base.AbstractProviderListTest.TestSpec;

import net.datafaker.providers.base.Tormenta;

import net.datafaker.providers.base.BaseFakerTest;

import java.util.List;
import java.util.Collection;

public class TormentaTest extends BaseFakerTest {
    @Override
    protected Collection<TestSpec> providerListTest(){
        Tormenta tmt=faker.tormenta();
        return List.of(TestSpec.of(tmt::bestiary, "tormenta.bestiary"),
            TestSpec.of(tmt::names, "tormenta.names"),
            TestSpec.of(tmt::cities, "tormenta.cities"));

    }

}
