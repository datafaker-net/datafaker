package net.datafaker;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnglandFootBallTest extends AbstractFakerTest {

    @Test
    public void testLeague() {
        String league = faker.englandfootball().league();
        assertThat(league).isNotEmpty();
    }

    @Test
    public void testTeam() {
        String team = faker.englandfootball().team();
        assertThat(team).isNotEmpty();
    }
}
