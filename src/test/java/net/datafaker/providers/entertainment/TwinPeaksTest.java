package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class TwinPeaksTest extends EntertainmentFakerTest {

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
