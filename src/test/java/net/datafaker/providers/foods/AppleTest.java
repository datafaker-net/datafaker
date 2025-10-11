package net.datafaker.providers.foods;

import net.datafaker.providers.food.Apple;

import java.util.Collection;
import java.util.List;

public class AppleTest extends FoodFakerTest {

    private final Apple apple = getFaker().apple();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(apple::type, "apple.type"),
            TestSpec.of(apple::color, "apple.color")
        );
    }
}
