package net.datafaker.providers.sport;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChessTest extends SportFakerTest {

    @Test
    void testPlayer() {
        assertThat(faker.chess().player()).isNotEmpty();
    }

    @Test
    void testTournament() {
        assertThat(faker.chess().tournament()).isNotEmpty();
    }

    @Test
    void testOpening() {
        assertThat(faker.chess().opening()).isNotEmpty();
    }

    @Test
    void testTitle() {
        assertThat(faker.chess().title()).isNotEmpty();
    }

}
