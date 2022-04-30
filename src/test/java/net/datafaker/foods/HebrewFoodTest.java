package net.datafaker.foods;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class HebrewFoodTest {
    public final String matchHebrewFood = "[\\u0590-\\u05FF ']+";
    public static Faker food;

    @BeforeEach
    void before() {
        food = new Faker(new Locale("he"));
    }

    @Test
    void hebrewIngredient() {
        assertThat(food.food().ingredient()).matches(matchHebrewFood);
    }

    @Test
    void hebrewFruit() {
        assertThat(food.food().fruit()).matches(matchHebrewFood);
    }

    @Test
    void hebrewVegetable() {
        assertThat(food.food().vegetable()).matches(matchHebrewFood);
    }

    @Test
    void hebrewSpice() {
        assertThat(food.food().spice()).matches(matchHebrewFood);
    }

    @Test
    void hebrewSushi() {
        assertThat(food.food().sushi()).matches(matchHebrewFood);
    }
}
