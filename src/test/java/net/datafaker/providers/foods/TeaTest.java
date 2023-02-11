package net.datafaker.providers.foods;

import net.datafaker.providers.food.Tea;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class TeaTest extends FoodFakerTest {

    private final Tea tea = getFaker().tea();

    @Test
    void testVariety() {
        assertThat(faker.tea().variety()).matches("^(?:[A-Z]['.\\-a-z]+[\\s-])*[A-Z]['.\\-a-z]+$");
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(tea::type, "tea.type")
        );
    }
}
