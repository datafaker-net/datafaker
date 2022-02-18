package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class DessertTest extends AbstractFakerTest {

    @Test
    public void variety() {
        assertThat(faker.dessert().variety(), matchesRegularExpression("[A-Za-z ]+"));
    }

    @Test
    public void topping() {
        assertThat(faker.dessert().topping(), matchesRegularExpression("[A-Za-z ]+"));
    }

    @Test
    public void flavor() {
        assertThat(faker.dessert().flavor(), matchesRegularExpression("[A-Za-z ']+"));
    }
}
