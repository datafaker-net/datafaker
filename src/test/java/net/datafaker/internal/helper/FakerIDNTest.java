package net.datafaker.internal.helper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FakerIDNTest {

    @Test
    void toASCIINoError() {
        assertThat(FakerIDN.toASCII("hello")).isEqualTo("hello");
    }

    @Test
    void toASCIIResultIsEmptyFallback() {
        // Even with un-convertible characters, should return a valid label, not throw
        String result = FakerIDN.toASCII("Ⱥ");
        assertThat(result).isNotEmpty();
        assertThat(result).matches("^[a-z0-9]([a-z0-9-]{0,61}[a-z0-9])?$");
    }

}
