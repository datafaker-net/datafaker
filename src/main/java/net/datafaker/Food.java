package net.datafaker;

/**
 * @since 0.8.0
 */
public class Food extends AbstractProvider {

    protected Food(Faker faker) {
        super(faker);
    }

    public String ingredient() {
        return faker.fakeValuesService().resolve("food.ingredients", this);
    }

    public String spice() {
        return faker.fakeValuesService().resolve("food.spices", this);
    }

    public String dish() {
        return faker.fakeValuesService().resolve("food.dish", this);
    }

    public String fruit() {
        return faker.fakeValuesService().resolve("food.fruits", this);
    }

    public String vegetable() {
        return faker.fakeValuesService().resolve("food.vegetables", this);
    }

    public String sushi() {
        return faker.fakeValuesService().resolve("food.sushi", this);
    }

    public String measurement() {
        return faker.fakeValuesService().resolve("food.measurement_sizes", this) +
            " " + faker.fakeValuesService().resolve("food.measurements", this);
    }
}
