package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class TwinPeaksTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.twinPeaks().character()).matches("^([\\w']+ ?){2,}$");
    }

    @Test
    public void location() {
        assertThat(faker.twinPeaks().location()).matches("^[A-Za-z0-9'&,\\- ]+$");
    }

    @Test
    public void quote() {
        assertThat(faker.twinPeaks().quote()).isNotEmpty();
    }
}
