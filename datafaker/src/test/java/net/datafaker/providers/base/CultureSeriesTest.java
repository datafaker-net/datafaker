package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class CultureSeriesTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        CultureSeries cultureSeries = faker.cultureSeries();
        return List.of(TestSpec.of(cultureSeries::books, "culture_series.books"),
                TestSpec.of(cultureSeries::cultureShips, "culture_series.culture_ships"),
                TestSpec.of(cultureSeries::cultureShipClasses, "culture_series.culture_ship_classes"),
                TestSpec.of(cultureSeries::cultureShipClassAbvs, "culture_series.culture_ship_class_abvs"),
                TestSpec.of(cultureSeries::civs, "culture_series.civs"),
                TestSpec.of(cultureSeries::planets, "culture_series.planets"));
    }

}
