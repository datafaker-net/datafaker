package net.datafaker;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

public class StockTest extends AbstractFakerTest {

    @Test
    public void testNasdaq() {
        assertThat(faker.stock().nsdqSymbol(), not(isEmptyOrNullString()));
    }

    @Test
    public void testNYSE() {
        assertThat(faker.stock().nyseSymbol(), not(isEmptyOrNullString()));
    }

}
