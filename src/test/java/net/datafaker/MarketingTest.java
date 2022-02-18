package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class MarketingTest extends AbstractFakerTest {

    @Test
    public void buzzwords() {
        assertThat(faker.marketing().buzzwords(), not(is(emptyOrNullString())));
    }

}