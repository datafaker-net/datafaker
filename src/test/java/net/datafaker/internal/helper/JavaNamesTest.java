package net.datafaker.internal.helper;

import org.junit.jupiter.api.Test;

import static net.datafaker.internal.helper.JavaNames.toJavaNames;
import static org.assertj.core.api.Assertions.assertThat;

class JavaNamesTest {
    @Test
    void variety() {
        assertThat(toJavaNames("variety", false)).isEqualTo("Variety");
        assertThat(toJavaNames("variety", true)).isEqualTo("variety");
    }

    @Test
    void metals() {
        assertThat(toJavaNames("metals", false)).isEqualTo("Metals");
        assertThat(toJavaNames("metals", true)).isEqualTo("metals");
    }

    @Test
    void empty_string() {
        assertThat(toJavaNames("", false)).isEqualTo("");
        assertThat(toJavaNames("", true)).isEqualTo("");
    }

    @Test
    void null_string() {
        assertThat(toJavaNames(null, false)).isNull();
        assertThat(toJavaNames(null, true)).isNull();
    }

    @Test
    void dota2() {
        assertThat(toJavaNames("dota2", false)).isEqualTo("Dota2");
        assertThat(toJavaNames("dota2", true)).isEqualTo("dota2");
    }

    @Test
    void oneChar() {
        assertThat(toJavaNames("x", false)).isEqualTo("X");
        assertThat(toJavaNames("x", true)).isEqualTo("x");
    }

    @Test
    void oneChar_underscore() {
        assertThat(toJavaNames("_", false)).isEqualTo("");
        assertThat(toJavaNames("_", true)).isEqualTo("");
    }

    @Test
    void big_bang_theory() {
        assertThat(toJavaNames("big_bang_theory", false)).isEqualTo("BigBangTheory");
        assertThat(toJavaNames("big_bang_theory", true)).isEqualTo("bigBangTheory");
    }

    @Test
    void upper_first_character_without_underscores() {
        assertThat(toJavaNames("A2013", false)).isEqualTo("A2013");
        assertThat(toJavaNames("A2013", true)).isEqualTo("a2013");
    }

    @Test
    void upper_first_character_with_underscores() {
        assertThat(toJavaNames("IATA_airline", false)).isEqualTo("IATAAirline");
        assertThat(toJavaNames("IATA_airline", true)).isEqualTo("iATAAirline");
    }

    @Test
    void endsWithUnderscore() {
        assertThat(toJavaNames("name_", false)).isEqualTo("Name");
        assertThat(toJavaNames("name_", true)).isEqualTo("name");
    }
}
