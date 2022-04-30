package net.datafaker.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FakerIDNTest {

    @Test
    void toASCIINoError() {
        assertThat(FakerIDN.toASCII("hello")).isEqualTo("hello");
    }
}
