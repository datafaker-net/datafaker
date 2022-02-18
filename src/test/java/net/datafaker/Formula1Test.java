package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class Formula1Test extends AbstractFakerTest {

    @Test
    public void driver() {
        assertThat(faker.formula1().driver(), matchesRegularExpression("[A-Za-zà-úÀ-Ú- ]+"));
    }

    @Test
    public void team() {
        assertThat(faker.formula1().team(), matchesRegularExpression("[A-Za-zà-úÀ-Ú- ]+"));
    }

    @Test
    public void circuit() {
        assertThat(faker.formula1().circuit(), matchesRegularExpression("[A-Za-zà-úÀ-Ú- ]+"));
    }

    @Test
    public void grandPrix() {
        assertThat(faker.formula1().grandPrix(), matchesRegularExpression("[A-Za-zà-úÀ-Ú- ]+"));
    }
}
