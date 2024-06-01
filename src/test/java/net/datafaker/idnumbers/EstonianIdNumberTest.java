package net.datafaker.idnumbers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EstonianIdNumberTest {
    /**
     * Samples from <a href="https://et.wikipedia.org/wiki/Isikukood">...</a>
     */
    @Test
    void checksum() {
        assertThat(EstonianIdNumber.checksum("3760503029")).isEqualTo(9);
        assertThat(EstonianIdNumber.checksum("3450123421")).isEqualTo(5);
        assertThat(EstonianIdNumber.checksum("4940313652")).isEqualTo(6);
        assertThat(EstonianIdNumber.checksum("5110712176")).isEqualTo(0);
        assertThat(EstonianIdNumber.checksum("6110712176")).isEqualTo(0);
    }
}
