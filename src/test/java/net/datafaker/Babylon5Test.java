package net.datafaker;

import org.junit.jupiter.api.Test;

import static net.datafaker.matchers.IsStringWithContents.isStringWithContents;
import static org.hamcrest.MatcherAssert.assertThat;

public class Babylon5Test extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.babylon5().character(), isStringWithContents());
    }

    @Test
    public void quote() {
        assertThat(faker.babylon5().quote(), isStringWithContents());
    }

}