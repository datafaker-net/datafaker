package net.datafaker.providers.foods;

import net.datafaker.providers.food.IceCream;

import java.util.Collection;
import java.util.List;

public class IceCreamTest extends FoodFakerTest {

    private final IceCream iceCream = getFaker().iceCream();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(iceCream::flavor, "ice_cream.flavor"),
            TestSpec.of(iceCream::shape, "ice_cream.shape"),
            TestSpec.of(iceCream::color, "ice_cream.color")
        );
    }

}
