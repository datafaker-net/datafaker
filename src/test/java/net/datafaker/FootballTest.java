package net.datafaker;

import net.datafaker.sport.AbstractSportFakerTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FootballTest extends AbstractSportFakerTest {

    @Test
    public void teams() {
        assertThat(faker.football().teams()).isNotEmpty();
    }

    @Test
    public void players() {
        assertThat(faker.football().players()).isNotEmpty();
    }

    @Test
    public void coaches() {
        assertThat(faker.football().coaches()).isNotEmpty();
    }

    @Test
    public void competitions() {
        assertThat(faker.football().competitions()).isNotEmpty();
    }

    @Test
    public void positions() {
        assertThat(faker.football().positions()).isNotEmpty();
    }

}
