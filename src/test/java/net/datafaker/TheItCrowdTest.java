package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class TheItCrowdTest extends AbstractFakerTest {

    @Test
    public void actors() {
        assertThat(faker.theItCrowd().actors(), not(is(emptyOrNullString())));
    }

    @Test
    public void characters() {
        assertThat(faker.theItCrowd().characters(), not(is(emptyOrNullString())));
    }

    @Test
    public void emails() {
        assertThat(faker.theItCrowd().emails(), not(is(emptyOrNullString())));
    }

    @Test
    public void quotes() {
        assertThat(faker.theItCrowd().quotes(), not(is(emptyOrNullString())));
    }

}