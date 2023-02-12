package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class RockBandTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        RockBand band = faker.rockBand();
        return List.of(TestSpec.of(band::name, "rock_band.name", "([\\w'/.,&]+ ?)+"));
    }
}
