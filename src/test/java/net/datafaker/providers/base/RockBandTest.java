package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RockBandTest extends BaseFakerTest<BaseFaker> {

    @Test
    void name() {
        assertThat(faker.rockBand().name()).matches("([\\w'/.,&]+ ?)+");
    }
}
