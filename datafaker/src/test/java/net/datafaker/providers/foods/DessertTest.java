package net.datafaker.providers.foods;

import net.datafaker.providers.food.Dessert;

import java.util.List;
import java.util.Collection;

class DessertTest extends FoodFakerTest {

    private final Dessert dessert = getFaker().dessert();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(dessert::flavor, "dessert.flavor"),
            TestSpec.of(dessert::topping, "dessert.topping"),
            TestSpec.of(dessert::variety, "dessert.variety")
        );
    }
}
