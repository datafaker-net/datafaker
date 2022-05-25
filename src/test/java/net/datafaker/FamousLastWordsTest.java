package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FamousLastWordsTest extends AbstractFakerTest {

    @Test
    void testLastWords() {
        assertThat(faker.famousLastWords().lastWords()).matches("^[A-Za-z- .,'!?-]+$");
    }
}
