package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeerTest extends AbstractFakerTest {

    @Test
    void testName() {
        assertThat(faker.beer().name()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    void testStyle() {
        assertThat(faker.beer().style()).matches("[A-Za-z'() 0-9-]+");
    }

    @Test
    void testHop() {
        assertThat(faker.beer().hop()).matches("[A-Za-z'’(). 0-9-]+");
    }

    @Test
    void testMalt() {
        assertThat(faker.beer().malt()).matches("[A-Za-z'() 0-9-]+");
    }

    @Test
    void testYeast() {
        assertThat(faker.beer().yeast()).matches("[\\p{L}'() 0-9-ö]+");
    }
}
