package net.datafaker.providers.foods;

import net.datafaker.providers.base.BaseFakerTest;
import net.datafaker.providers.food.FoodFaker;

public class FoodFakerTest extends BaseFakerTest<FoodFaker> {
    @Override
    protected FoodFaker getFaker() {
        return new FoodFaker();
    }
}
