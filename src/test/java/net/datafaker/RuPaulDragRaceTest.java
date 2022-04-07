package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

public class RuPaulDragRaceTest extends AbstractFakerTest {

    @RepeatedTest(100)
    public void queens() {
        assertThat(faker.ruPaulDragRace().queen()).matches("([\\w'/.,&]+ ?)+");
    }

    @RepeatedTest(100)
    public void quotes() {
        assertThat(faker.ruPaulDragRace().quote()).matches("([\\w'/.,\\-!&?\"]+ ?)+");
    }
}
