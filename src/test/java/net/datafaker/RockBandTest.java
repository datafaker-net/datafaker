package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RockBandTest extends AbstractFakerTest {

    @Test
    void name() {
        assertThat(faker.rockBand().name()).matches("([\\w'/.,&]+ ?)+");
    }
}
