package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class RockBandTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        RockBand band = faker.rockBand();
        return Arrays.asList(TestSpec.of(band::name, "rock_band.name", "([\\w'/.,&]+ ?)+"));
    }
}
