package net.datafaker;


import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class EnglandFootBallTest extends AbstractFakerTest {

    @Test
    public void testLeague() {
        String league = faker.englandfootball().league();
        assertThat(league, not(is(emptyOrNullString())));
    }

    @Test
    public void testTeam() {
        String team = faker.englandfootball().team();
        assertThat(team, not(is(emptyOrNullString())));

    }
}
