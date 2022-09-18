package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class TwinPeaksTest extends AbstractBaseFakerTest {

    @Test
    void character() {
        assertThat(faker.twinPeaks().character()).matches("^([\\w']+ ?){2,}$");
    }

    @Test
    void location() {
        assertThat(faker.twinPeaks().location()).matches("^[A-Za-z\\d'&,\\- ]+$");
    }

    @Test
    void quote() {
        assertThat(faker.twinPeaks().quote()).isNotEmpty();
    }
}
