package net.datafaker.providers.movie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


class DuneTest extends MovieFakerTest {

    @Test
    void character() {
        Assertions.assertThat(faker.dune().character()).matches("[A-Za-z '\\-\"]+");
    }

    @Test
    void title() {
        Assertions.assertThat(faker.dune().title()).matches("[A-Za-z ]+");
    }

    @Test
    void planet() {
        Assertions.assertThat(faker.dune().planet()).matches("[A-Za-z ]+");
    }

    @Test
    void quote() {
        Assertions.assertThat(faker.dune().quote()).matches("\\P{Cc}+");
    }

    @RepeatedTest(100)
    void randomQuote() {
        Assertions.assertThat(
            faker.dune().quote(faker.options().option(Dune.Quote.class))).matches("\\P{Cc}+");
    }

    @Test
    void saying() {
        Assertions.assertThat(faker.dune().saying()).matches("\\P{Cc}+");
    }

    @RepeatedTest(100)
    void randomSaying() {
        Assertions.assertThat(
            faker.dune().saying(faker.options().option(Dune.Saying.class))).matches("\\P{Cc}+");
    }

}
