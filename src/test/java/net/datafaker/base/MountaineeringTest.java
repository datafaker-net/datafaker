package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MountaineeringTest extends AbstractBaseFakerTest {

    @Test
    void mountaineer() {
        assertThat(faker.mountaineering().mountaineer()).isNotEmpty();
    }
}

