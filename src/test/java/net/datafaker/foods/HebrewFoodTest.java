package net.datafaker.foods;

import net.datafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class HebrewFoodTest {
    public final String matchHebrewFood = "[\\u0590-\\u05FF ']+";
    public static Faker food;

    @Before
    public void before() {
        food = new Faker(new Locale("he"));
    }

    @Test
    public void hebrewIngredient() {
        assertThat(food.food().ingredient(), matchesRegularExpression(matchHebrewFood));
    }

    @Test
    public void hebrewFruit() {
        assertThat(food.food().fruit(), matchesRegularExpression(matchHebrewFood));
    }

    @Test
    public void hebrewVegetable() {
        assertThat(food.food().vegetable(), matchesRegularExpression(matchHebrewFood));
    }

    @Test
    public void hebrewSpice() {
        assertThat(food.food().spice(), matchesRegularExpression(matchHebrewFood));
    }

    @Test
    public void hebrewSushi() {
        assertThat(food.food().sushi(), matchesRegularExpression(matchHebrewFood));
    }
}
