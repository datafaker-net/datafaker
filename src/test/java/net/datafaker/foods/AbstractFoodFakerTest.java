package net.datafaker.foods;

import net.datafaker.base.BaseFakerTest;
import net.datafaker.food.FoodFaker;

public class AbstractFoodFakerTest extends BaseFakerTest<FoodFaker> {
    @Override
    protected FoodFaker getFaker() {
        return new FoodFaker();
    }
}
