package net.datafaker.providers.food;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Food extends AbstractProvider<FoodProviders> {

    protected Food(FoodProviders faker) {
        super(faker);
    }

    public String ingredient() {
        return resolve("food.ingredients");
    }

    public String allergen() {
        return resolve("food.allergens");
    }

    public String spice() {
        return resolve("food.spices");
    }

    public String dish() {
        return resolve("food.dish");
    }

    public String fruit() {
        return resolve("food.fruits");
    }

    public String vegetable() {
        return resolve("food.vegetables");
    }

    public String sushi() {
        return resolve("food.sushi");
    }

    public String measurement() {
        return resolve("food.measurement_sizes") +
            " " + resolve("food.measurements");
    }
}
