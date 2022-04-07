package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BeerTest extends AbstractFakerTest {

    @Test
    public void testName() {
        assertThat(faker.beer().name()).matches("[\\p{L}'()., 0-9-’]+");
    }

    @Test
    public void testStyle() {
        assertThat(faker.beer().style()).matches("[A-Za-z'() 0-9-]+");
    }

    @Test
    public void testHop() {
        assertThat(faker.beer().hop()).matches("[A-Za-z'’(). 0-9-]+");
    }

    @Test
    public void testMalt() {
        assertThat(faker.beer().malt()).matches("[A-Za-z'() 0-9-]+");
    }

    @Test
    public void testYeast() {
        assertThat(faker.beer().yeast()).matches("[\\p{L}'() 0-9-ö]+");
    }
}
