package net.datafaker.internal.helper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WordUtilsTest {

    @Test
    void capitalizeWord() {
        assertThat(WordUtils.capitalizeWord("hello, world")).isEqualTo("Hello, world");
        assertThat(WordUtils.capitalizeWord("w")).isEqualTo("W");
        assertThat(WordUtils.capitalizeWord("W")).isEqualTo("W");

        assertThat(WordUtils.capitalizeWord("word")).isEqualTo("Word");
        assertThat(WordUtils.capitalizeWord("Word")).isEqualTo("Word");
        assertThat(WordUtils.capitalizeWord("WORD")).isEqualTo("WORD");
        assertThat(WordUtils.capitalizeWord("wORD")).isEqualTo("WORD");
        assertThat(WordUtils.capitalizeWord("_word")).isEqualTo("_word");
        assertThat(WordUtils.capitalizeWord("2word")).isEqualTo("2word");
        assertThat(WordUtils.capitalizeWord("")).isEmpty();
    }

    @Test
    void capitalizeNull() {
        assertThat(WordUtils.capitalize(null)).isNull();
        assertThat(WordUtils.capitalize("")).isEmpty();
    }
}
