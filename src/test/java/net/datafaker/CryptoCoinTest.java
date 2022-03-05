package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class CryptoCoinTest extends AbstractFakerTest {

    @Test
    public void coin() {
        assertThat(faker.cryptoCoin().coin(), not(is(emptyOrNullString())));
    }

}
