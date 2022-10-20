package net.datafaker.providers.sport;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnglandFootBallTest extends SportFakerTest {

    @Test
    void league() {
        String league = faker.englandfootball().league();
        assertThat(league).isNotEmpty();
    }

    @Test
    void team() {
        String team = faker.englandfootball().team();
        assertThat(team).isNotEmpty();
    }
}
