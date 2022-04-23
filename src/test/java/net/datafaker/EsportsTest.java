package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EsportsTest extends AbstractFakerTest {

    @Test
    public void player() {
        assertThat(faker.esports().player()).matches("(\\w|.)+");
    }

    @Test
    public void team() {
        assertThat(faker.esports().team()).matches("((\\w|.)+ ?)+");
    }

    @Test
    public void event() {
        assertThat(faker.esports().event()).matches("(\\w+ ?)+");
    }

    @Test
    public void league() {
        assertThat(faker.esports().league()).matches("\\w+");
    }

    @Test
    public void game() {
        assertThat(faker.esports().game()).matches("([\\w:.]+ ?)+");
    }
}
