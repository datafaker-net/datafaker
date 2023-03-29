package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Strings.isNullOrEmpty;

class OscarMovieTest extends EntertainmentFakerTest {

    private final OscarMovie oscarMovie = getFaker().oscarMovie();

    @RepeatedTest(100)
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

    @RepeatedTest(100)
    void character() {
        assertThat(oscarMovie.character()).matches("\\P{Cc}+");
    }

    @RepeatedTest(100)
    void releaseDate() {
        assertThat(oscarMovie.releaseDate()).matches("[A-Za-z,0-9 ]+");
    }

}
