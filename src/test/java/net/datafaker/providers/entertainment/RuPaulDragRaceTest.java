package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class RuPaulDragRaceTest extends EntertainmentFakerTest {

    @RepeatedTest(100)
    void queens() {
        assertThat(faker.ruPaulDragRace().queen()).matches("([\\w'/.,&]+ ?)+");
    }

    @RepeatedTest(100)
    void quotes() {
        assertThat(faker.ruPaulDragRace().quote()).matches("([\\w'/.,\\-!&?\"]+ ?)+");
    }
}
