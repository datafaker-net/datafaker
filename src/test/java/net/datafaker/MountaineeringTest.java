package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MountaineeringTest extends AbstractFakerTest {

    @Test
    void mountaineer() {
        assertThat(faker.mountaineering().mountaineer()).isNotEmpty();
    }
}

