package net.datafaker.providers.foods;

import net.datafaker.providers.base.ProviderListTest;
import net.datafaker.providers.food.FoodFaker;

public abstract class FoodFakerTest extends ProviderListTest<FoodFaker> {

    protected final FoodFaker faker = new FoodFaker();

    @Override
    protected final FoodFaker getFaker() {
        return faker;
    }
}
