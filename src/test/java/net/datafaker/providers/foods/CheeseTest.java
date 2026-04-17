package net.datafaker.providers.foods;

import net.datafaker.providers.food.Cheese;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CheeseTest extends FoodFakerTest {

    private final Cheese cheese = getFaker().cheese();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(cheese::type, "cheese.type"),
            TestSpec.of(cheese::texture, "cheese.texture"),
            TestSpec.of(cheese::milk, "cheese.milk"),
            TestSpec.of(cheese::colour, "cheese.colour"),
            TestSpec.of(cheese::name, "cheese.name")
        );
    }

    @Test
    void shouldReturnCheeseWedge() {
        Cheese.Wedge wedge = cheese.wedge();
        assertThat(wedge.name()).isNotEmpty();
        assertThat(wedge.type()).isNotEmpty();
        assertThat(wedge.texture()).isNotEmpty();
        assertThat(wedge.colour()).isNotEmpty();
        assertThat(wedge.milk()).isNotEmpty();
    }
}
