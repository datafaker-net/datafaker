package net.datafaker.base;

import net.datafaker.AbstractFakerTest;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class FamousLastWordsTest extends AbstractFakerTest {

    @RepeatedTest(1000)
    void testLastWords() {
        assertThat(faker.famousLastWords().lastWords()).matches("^[A-Za-z- .,'!?-…]+$");
    }
}
