package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DuneTest extends AbstractFakerTest {

    @Test
    void character() {
        assertThat(faker.dune().character()).matches("[A-Za-z '\\-\"]+");
    }

    @Test
    void title() {
        assertThat(faker.dune().title()).matches("[A-Za-z ]+");
    }

    @Test
    void planet() {
        assertThat(faker.dune().planet()).matches("[A-Za-z ]+");
    }

    @Test
    void quote() {
        assertThat(faker.dune().quote()).matches("\\P{Cc}+");
    }

    @RepeatedTest(100)
    void randomQuote() {
        assertThat(
            faker.dune().quote(faker.options().option(Dune.Quote.class))).matches("\\P{Cc}+");
    }

    @Test
    void saying() {
        assertThat(faker.dune().saying()).matches("\\P{Cc}+");
    }

    @RepeatedTest(100)
    void randomSaying() {
        assertThat(
            faker.dune().saying(faker.options().option(Dune.Saying.class))).matches("\\P{Cc}+");
    }

}
