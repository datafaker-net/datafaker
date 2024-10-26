package net.datafaker.providers.foods;

import net.datafaker.providers.food.Coffee;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class CoffeeTest extends FoodFakerTest {

    @Test
    void notes() {
        assertThat(faker.coffee().notes()).isNotEmpty();
    }

    @Test
    void blendName() {
        assertThat(faker.coffee().blendName()).isNotEmpty();
    }

    private final Coffee coffee = getFaker().coffee();

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(
            TestSpec.of(coffee::body, "coffee.body"),
            TestSpec.of(coffee::country, "coffee.country"),
            TestSpec.of(coffee::descriptor, "coffee.descriptor"),
            TestSpec.of(coffee::intensifier, "coffee.intensifier"),
            TestSpec.of(coffee::name1, "coffee.name_1"),
            TestSpec.of(coffee::name2, "coffee.name_2"),
            TestSpec.of(() -> coffee.region(Coffee.Country.BRAZIL), "coffee.regions.brazil"),
            TestSpec.of(() -> coffee.region(Coffee.Country.BURUNDI), "coffee.regions.burundi"),
            TestSpec.of(() -> coffee.region(Coffee.Country.COLOMBIA), "coffee.regions.colombia"),
            TestSpec.of(() -> coffee.region(Coffee.Country.COSTA_RICA), "coffee.regions.costa_rica"),
            TestSpec.of(() -> coffee.region(Coffee.Country.EL_SALVADOR), "coffee.regions.el_salvador"),
            TestSpec.of(() -> coffee.region(Coffee.Country.ETHIOPIA), "coffee.regions.ethiopia"),
            TestSpec.of(() -> coffee.region(Coffee.Country.GUATEMALA), "coffee.regions.guatemala"),
            TestSpec.of(() -> coffee.region(Coffee.Country.HONDURAS), "coffee.regions.honduras"),
            TestSpec.of(() -> coffee.region(Coffee.Country.INDIA), "coffee.regions.india"),
            TestSpec.of(() -> coffee.region(Coffee.Country.KENYA), "coffee.regions.kenya"),
            TestSpec.of(() -> coffee.region(Coffee.Country.MEXICO), "coffee.regions.mexico"),
            TestSpec.of(() -> coffee.region(Coffee.Country.NICARAGUA), "coffee.regions.nicaragua"),
            TestSpec.of(() -> coffee.region(Coffee.Country.PANAMA), "coffee.regions.panama"),
            TestSpec.of(() -> coffee.region(Coffee.Country.RWANDA), "coffee.regions.rwanda"),
            TestSpec.of(() -> coffee.region(Coffee.Country.SUMATRA), "coffee.regions.sumatra"),
            TestSpec.of(() -> coffee.region(Coffee.Country.TANZANIA), "coffee.regions.tanzania"),
            TestSpec.of(() -> coffee.region(Coffee.Country.UGANDA), "coffee.regions.uganda"),
            TestSpec.of(() -> coffee.region(Coffee.Country.YEMEN), "coffee.regions.yemen"),
            TestSpec.of(coffee::variety, "coffee.variety")
        );
    }
}
