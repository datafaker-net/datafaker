package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class HeyArnoldTest extends AbstractFakerTest {

    @Test
    public void characters() {
        assertThat(faker.heyArnold().characters(), not(is(emptyOrNullString())));
    }

    @Test
    public void locations() {
        assertThat(faker.heyArnold().locations(), not(is(emptyOrNullString())));
    }

    @Test
    public void quotes() {
        assertThat(faker.heyArnold().quotes(), not(is(emptyOrNullString())));
    }

}
