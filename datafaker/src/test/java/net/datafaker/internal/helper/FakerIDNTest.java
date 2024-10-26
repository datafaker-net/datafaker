package net.datafaker.internal.helper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FakerIDNTest {

    @Test
    void toASCIINoError() {
        assertThat(FakerIDN.toASCII("hello")).isEqualTo("hello");
    }

    @Test
    void toASCIIResultIsEmptyException() { // http://Ⱥbby.com
        assertThatThrownBy(() -> FakerIDN.toASCII("Ⱥ"))
            .isInstanceOf(RuntimeException.class);
    }

}
