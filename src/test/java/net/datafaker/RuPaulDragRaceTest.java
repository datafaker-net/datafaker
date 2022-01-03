package net.datafaker;

import net.datafaker.repeating.Repeat;
import org.junit.Test;

import static net.datafaker.matchers.MatchesRegularExpression.matchesRegularExpression;
import static org.hamcrest.MatcherAssert.assertThat;

public class RuPaulDragRaceTest extends AbstractFakerTest {

    @Test
    @Repeat(times = 100)
    public void queens() {
        assertThat(faker.ruPaulDragRace().queen(), matchesRegularExpression("([\\w'/.,&]+ ?)+"));
    }

    @Test
    @Repeat(times = 100)
    public void quotes() {
        assertThat(faker.ruPaulDragRace().quote(), matchesRegularExpression("([\\w'/.,\\-!&?\"]+ ?)+"));
    }
}
