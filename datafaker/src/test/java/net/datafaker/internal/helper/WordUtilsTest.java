package net.datafaker.internal.helper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WordUtilsTest {

    @Test
    void capitalizeWord() {
        assertThat(WordUtils.capitalize("w")).isEqualTo("W");
        assertThat(WordUtils.capitalize("W")).isEqualTo("W");

        assertThat(WordUtils.capitalize("word")).isEqualTo("Word");
        assertThat(WordUtils.capitalize("Word")).isEqualTo("Word");
        assertThat(WordUtils.capitalize("WORD")).isEqualTo("WORD");
        assertThat(WordUtils.capitalize("wORD")).isEqualTo("WORD");
    }

    @Test
    void capitalizeNull() {
        assertThat(WordUtils.capitalize(null)).isNull();
        assertThat(WordUtils.capitalize("")).isEmpty();
    }
}
