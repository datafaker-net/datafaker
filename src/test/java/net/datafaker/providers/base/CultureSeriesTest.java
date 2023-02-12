package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class CultureSeriesTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.cultureSeries().books(), "culture_series.books"),
                TestSpec.of(() -> faker.cultureSeries().cultureShips(), "culture_series.culture_ships"),
                TestSpec.of(() -> faker.cultureSeries().cultureShipClasses(), "culture_series.culture_ship_classes"),
                TestSpec.of(() -> faker.cultureSeries().cultureShipClassAbvs(), "culture_series.culture_ship_class_abvs"),
                TestSpec.of(() -> faker.cultureSeries().civs(), "culture_series.civs"),
                TestSpec.of(() -> faker.cultureSeries().planets(), "culture_series.planets"));
    }

}
