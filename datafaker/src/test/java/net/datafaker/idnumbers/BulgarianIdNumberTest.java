package net.datafaker.idnumbers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BulgarianIdNumberTest {
    private final BulgarianIdNumber generator = new BulgarianIdNumber();

    @Test
    void checksum() {
        assertThat(generator.checksum("803205603")).isEqualTo(1);
        assertThat(generator.checksum("800101000")).isEqualTo(8);
        assertThat(generator.checksum("750102001")).isEqualTo(8);
        assertThat(generator.checksum("820630876")).isEqualTo(0);
        assertThat(generator.checksum("560628204")).isEqualTo(7);
        assertThat(generator.checksum("752316926")).isEqualTo(3);
        assertThat(generator.checksum("755201000")).isEqualTo(5);
        assertThat(generator.checksum("754201103")).isEqualTo(0);
    }
}
