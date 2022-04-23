package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RockBandTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.rockBand().name()).matches("([\\w'/.,&]+ ?)+");
    }
}
