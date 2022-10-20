package net.datafaker.providers.foods;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeerTest extends FoodFakerTest {

    @Test
    void name() {
        assertThat(faker.beer().name()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void style() {
        assertThat(faker.beer().style()).matches("[A-Za-z'() 0-9-]+");
    }

    @Test
    void hop() {
        assertThat(faker.beer().hop()).matches("[A-Za-z'’(). 0-9-]+");
    }

    @Test
    void malt() {
        assertThat(faker.beer().malt()).matches("[A-Za-z'() 0-9-]+");
    }

    @Test
    void yeast() {
        assertThat(faker.beer().yeast()).matches("[\\p{L}'() 0-9-ö]+");
    }
}
