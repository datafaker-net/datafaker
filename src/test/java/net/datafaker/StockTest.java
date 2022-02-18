package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class StockTest extends AbstractFakerTest {

    @Test
    public void testNasdaq() {
        assertThat(faker.stock().nsdqSymbol(), not(is(emptyOrNullString())));
    }

    @Test
    public void testNYSE() {
        assertThat(faker.stock().nyseSymbol(), not(is(emptyOrNullString())));
    }

}
