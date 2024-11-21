package net.datafaker.idnumbers;

import static java.lang.Character.toUpperCase;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LatinLettersTest {

    @ParameterizedTest
    @ValueSource(chars = {'a', 'e', 'i', 'o', 'u'})
    void vowels(char vowel) {
        assertThat(LatinLetters.isConsonant(vowel)).isFalse();
        assertThat(LatinLetters.isConsonant(toUpperCase(vowel))).isFalse();
    }

    @ParameterizedTest
    @ValueSource(chars = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'})
    void consonants(char consonant) {
        assertThat(LatinLetters.isConsonant(consonant)).isTrue();
        assertThat(LatinLetters.isConsonant(toUpperCase(consonant))).isTrue();
    }

    @Test
    void keepsOnlyLatinLetters() {
        assertThat(LatinLetters.removeNonLatinLetters("John 1 Malkovi4")).isEqualTo("JohnMalkovi");
        assertThat(LatinLetters.removeNonLatinLetters("πüäÄöÖ")).isEqualTo("");
        assertThat(LatinLetters.removeNonLatinLetters("Ülar")).isEqualTo("lar");
        assertThat(LatinLetters.removeNonLatinLetters("Андрей the Творожок")).isEqualTo("the");
    }

}
