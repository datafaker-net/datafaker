package net.datafaker.service.files;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnFileTest {

    /**
     * To adhere to conventions, please use lowercase names only.
     */
    @Test
    void fileNameConvention() {
        assertThat(EnFile.getFiles()).allSatisfy(e -> assertThat(e.getFile()).isLowerCase());
    }
}
