package net.datafaker.foods;

import net.datafaker.base.AbstractBaseFakerTest;
import net.datafaker.food.FoodFaker;

public class AbstractFoodFakerTest extends AbstractBaseFakerTest<FoodFaker> {
    @Override
    protected FoodFaker getFaker() {
        return new FoodFaker();
    }
}
