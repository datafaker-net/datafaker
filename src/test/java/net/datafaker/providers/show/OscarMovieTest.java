package net.datafaker.providers.show;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.isNullOrEmpty;

class OscarMovieTest extends MovieFakerTest {

    @RepeatedTest(100)
    void actor() {
        assertThat(faker.oscarMovie().actor()).matches("\\P{Cc}+");
    }

    @RepeatedTest(10)
    void movieName() {
        assertThat(isNullOrEmpty(faker.oscarMovie().movieName())).isFalse();
    }

    @RepeatedTest(10)
    void quote() {
        assertThat(isNullOrEmpty(faker.oscarMovie().quote())).isFalse();
    }

    @RepeatedTest(100)
    void character() {
        assertThat(faker.oscarMovie().character()).matches("\\P{Cc}+");
    }

    @RepeatedTest(100)
    void releaseDate() {
        assertThat(faker.oscarMovie().releaseDate()).matches("[A-Za-z,0-9 ]+");
    }
}
