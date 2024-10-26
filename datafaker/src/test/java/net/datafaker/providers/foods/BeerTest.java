package net.datafaker.providers.foods;

import net.datafaker.providers.food.Beer;

import java.util.List;
import java.util.Collection;

class BeerTest extends FoodFakerTest {

    private final Beer beer = getFaker().beer();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(beer::brand, "beer.brand"),
            TestSpec.of(beer::hop, "beer.hop"),
            TestSpec.of(beer::malt, "beer.malt"),
            TestSpec.of(beer::name, "beer.name"),
            TestSpec.of(beer::style, "beer.style"),
            TestSpec.of(beer::yeast, "beer.yeast")
        );
    }
}
