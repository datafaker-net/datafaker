package net.datafaker.providers.foods;

import net.datafaker.providers.food.Food;
import net.datafaker.providers.food.FoodFaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collection;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class HebrewFoodTest extends FoodFakerTest {
    private Food food = getFaker().food();

    @BeforeEach
    protected void before() {
        food = getFaker().food();
    }

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
