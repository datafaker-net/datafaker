package net.datafaker.foods;

import net.datafaker.base.BaseFakerTest;
import net.datafaker.food.FoodFaker;

public class FoodFakerTest extends BaseFakerTest<FoodFaker> {
    @Override
    protected FoodFaker getFaker() {
        return new FoodFaker();
    }
}
