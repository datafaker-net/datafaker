package net.datafaker.providers.entertainment;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.isNullOrEmpty;

class OscarMovieTest {
    private final OscarMovie oscarMovie = new Faker().oscarMovie();

    @RepeatedTest(10)
    void actor() {
        assertThat(oscarMovie.actor()).matches("\\P{Cc}+");
    }

    @RepeatedTest(10)
    void movieName() {
        assertThat(isNullOrEmpty(oscarMovie.movieName())).isFalse();
    }

    @RepeatedTest(10)
    void quote() {
        assertThat(isNullOrEmpty(oscarMovie.quote())).isFalse();
    }

    @RepeatedTest(10)
    void character() {
        assertThat(oscarMovie.character()).matches("\\P{Cc}+");
    }

    @RepeatedTest(10)
    void releaseDate() {
        assertThat(oscarMovie.releaseDate()).matches("[A-Za-z,0-9 ]+");
    }

}
