package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class AustraliaTest extends AbstractFakerTest {

    @Test
    public void locations() { assertThat(faker.australia().locations(), not(is(emptyOrNullString())));
    }

    @Test
    public void animals() { assertThat(faker.australia().animals(), not(is(emptyOrNullString())));
    }

    @Test
    public void states() {
        assertThat(faker.australia().states(), not(is(emptyOrNullString())));
    }

}
