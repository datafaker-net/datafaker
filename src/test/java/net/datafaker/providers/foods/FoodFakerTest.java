package net.datafaker.providers.foods;

import net.datafaker.providers.base.BaseFakerTest;
import net.datafaker.providers.food.FoodFaker;

public abstract class FoodFakerTest extends BaseFakerTest<FoodFaker> {
    @Override
    protected final FoodFaker getFaker() {
        return new FoodFaker();
    }
}
