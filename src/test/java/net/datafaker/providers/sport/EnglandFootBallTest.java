package net.datafaker.providers.sport;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnglandFootBallTest extends SportFakerTest {

    @Test
    void testLeague() {
        String league = faker.englandfootball().league();
        assertThat(league).isNotEmpty();
    }

    @Test
    void testTeam() {
        String team = faker.englandfootball().team();
        assertThat(team).isNotEmpty();
    }
}
