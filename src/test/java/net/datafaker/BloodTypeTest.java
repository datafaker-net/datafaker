package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class BloodTypeTest extends AbstractFakerTest {

    @Test
    public void abo_types() {
        assertThat(faker.bloodtype().abo_types(), matchesRegularExpression("[A-Za-z]+"));
    }

    @Test
    public void rh_types() {
        assertThat(faker.bloodtype().rh_types(), matchesRegularExpression("[A-Za-z\\+\\-]+"));
    }

    @Test
    public void p_types() {
        assertThat(faker.bloodtype().p_types(), matchesRegularExpression("[A-Za-z0-9]+"));
    }

}
