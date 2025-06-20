package net.datafaker.providers.foods;

import net.datafaker.providers.food.Food;
import net.datafaker.providers.food.FoodFaker;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collection;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

final class HebrewFoodTest extends FoodFakerTest {
    private final Food food = getFaker().food();

    @Override
    protected FoodFaker getFaker() {
        return new FoodFaker(new Locale("he"));
    }

    @Test
    void measurement() {
        assertThat(food.measurement()).matches("([A-Za-z1-9/ ]+){2}");
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(food::dish, "food.dish"),
            TestSpec.of(food::fruit, "food.fruits"),
            TestSpec.of(food::ingredient, "food.ingredients"),
            TestSpec.of(food::spice, "food.spices"),
            TestSpec.of(food::sushi, "food.sushi"),
            TestSpec.of(food::vegetable, "food.vegetables")
        );
    }
}
