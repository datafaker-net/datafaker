package net.datafaker.idnumbers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MacedonianIdNumberTest {
    private final MacedonianIdNumber generator = new MacedonianIdNumber();

    @Test
    void checksum() {
        assertThat(generator.checksum("010100650000")).isEqualTo(6);
        assertThat(generator.checksum("923456789012")).isEqualTo(4);
    }
}
