package net.datafaker.foods;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class HebrewFoodTest {
    public final String matchHebrewFood = "[\\u0590-\\u05FF ']+";
    public static Faker food;

    @BeforeEach
    public void before() {
        food = new Faker(new Locale("he"));
    }

    @Test
    public void hebrewIngredient() {
        assertThat(food.food().ingredient()).matches(matchHebrewFood);
    }

    @Test
    public void hebrewFruit() {
        assertThat(food.food().fruit()).matches(matchHebrewFood);
    }

    @Test
    public void hebrewVegetable() {
        assertThat(food.food().vegetable()).matches(matchHebrewFood);
    }

    @Test
    public void hebrewSpice() {
        assertThat(food.food().spice()).matches(matchHebrewFood);
    }

    @Test
    public void hebrewSushi() {
        assertThat(food.food().sushi()).matches(matchHebrewFood);
    }
}
