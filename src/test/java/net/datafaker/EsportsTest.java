package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EsportsTest extends AbstractFakerTest {

    @Test
    void player() {
        assertThat(faker.esports().player()).matches("(\\w|.)+");
    }

    @Test
    void team() {
        assertThat(faker.esports().team()).matches("((\\w|.)+ ?)+");
    }

    @Test
    void event() {
        assertThat(faker.esports().event()).matches("(\\w+ ?)+");
    }

    @Test
    void league() {
        assertThat(faker.esports().league()).matches("\\w+");
    }

    @Test
    void game() {
        assertThat(faker.esports().game()).matches("([\\w:.]+ ?)+");
    }
}
