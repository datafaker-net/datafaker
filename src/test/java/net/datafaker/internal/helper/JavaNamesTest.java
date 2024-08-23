package net.datafaker.internal.helper;

import org.junit.jupiter.api.Test;

import static net.datafaker.internal.helper.JavaNames.toJavaNames;
import static org.assertj.core.api.Assertions.assertThat;

class JavaNamesTest {
    @Test
    void singleWord() {
        assertThat(toJavaNames("variety", false)).isEqualTo("Variety");
        assertThat(toJavaNames("variety", true)).isEqualTo("variety");
    }

    @Test
    void emptyString() {
        assertThat(toJavaNames("", false)).isEqualTo("");
        assertThat(toJavaNames("", true)).isEqualTo("");
    }

    @Test
    void nullString() {
        assertThat(toJavaNames(null, false)).isNull();
        assertThat(toJavaNames(null, true)).isNull();
    }

    @Test
    void wordWithDigit() {
        assertThat(toJavaNames("dota2", false)).isEqualTo("Dota2");
        assertThat(toJavaNames("dota2", true)).isEqualTo("dota2");
    }

    @Test
    void wordStartingWithDigit() {
        assertThat(toJavaNames("1love", false)).isEqualTo("1love");
        assertThat(toJavaNames("1love", true)).isEqualTo("1love");
    }

    @Test
    void singleCharacter() {
        assertThat(toJavaNames("x", false)).isEqualTo("X");
        assertThat(toJavaNames("x", true)).isEqualTo("x");
    }

    @Test
    void singleCharacterUnderscore() {
        assertThat(toJavaNames("_", false)).isEqualTo("");
        assertThat(toJavaNames("_", true)).isEqualTo("");
    }

    @Test
    void onlyUnderscores() {
        assertThat(toJavaNames("__", false)).isEqualTo("");
        assertThat(toJavaNames("___", true)).isEqualTo("");
    }

    @Test
    void multipleWordsSeparatedByUnderscores() {
        assertThat(toJavaNames("big_bang_theory", false)).isEqualTo("BigBangTheory");
        assertThat(toJavaNames("big_bang_theory", true)).isEqualTo("bigBangTheory");
    }

    @Test
    void multipleUnderscoresInRow() {
        assertThat(toJavaNames("big__bang___theory", false)).isEqualTo("BigBangTheory");
        assertThat(toJavaNames("big__bang___theory", true)).isEqualTo("bigBangTheory");
    }

    @Test
    void upperFirstCharacterWithoutUnderscores() {
        assertThat(toJavaNames("A2013", false)).isEqualTo("A2013");
        assertThat(toJavaNames("A2013", true)).isEqualTo("a2013");
    }

    @Test
    void upperFirstCharacterWithUnderscores() {
        assertThat(toJavaNames("IATA_airline", false)).isEqualTo("IATAAirline");
        assertThat(toJavaNames("IATA_airline", true)).isEqualTo("iATAAirline");
    }

    @Test
    void endingWithUnderscore() {
        assertThat(toJavaNames("name_", false)).isEqualTo("Name");
        assertThat(toJavaNames("name_", true)).isEqualTo("name");
    }
}
