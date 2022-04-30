package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.isNullOrEmpty;

class OscarMovieTest extends AbstractFakerTest {

    @RepeatedTest(100)
    void actor() {
        assertThat(faker.oscarMovie().actor())
            .is(CONDITION_FUNCTION.apply(c -> Character.isLetter(c) || c == ' ' || c == '.' || c == '-' || c == '(' || c == ')' || c == '\''));
    }

    @Test
    void movieName() {
        assertThat(isNullOrEmpty(faker.oscarMovie().movieName())).isFalse();
    }

    @Test
    void quote() {
        assertThat(isNullOrEmpty(faker.oscarMovie().quote())).isFalse();
    }

    @RepeatedTest(100)
    void character() {
        assertThat(faker.oscarMovie().character())
            .is(CONDITION_FUNCTION.apply(c -> Character.isLetter(c) || c == ' ' || c == '.' || c == '-' || c == '\''));
    }

    @RepeatedTest(100)
    void releaseDate() {
        assertThat(faker.oscarMovie().releaseDate()).matches("[A-Za-z,0-9 ]+");
    }
}
