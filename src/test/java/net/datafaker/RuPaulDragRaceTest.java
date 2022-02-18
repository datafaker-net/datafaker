package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class RuPaulDragRaceTest extends AbstractFakerTest {

    @RepeatedTest(100)
    public void queens() {
        assertThat(faker.ruPaulDragRace().queen(), matchesRegularExpression("([\\w'/.,&]+ ?)+"));
    }

    @RepeatedTest(100)
    public void quotes() {
        assertThat(faker.ruPaulDragRace().quote(), matchesRegularExpression("([\\w'/.,\\-!&?\"]+ ?)+"));
    }
}
