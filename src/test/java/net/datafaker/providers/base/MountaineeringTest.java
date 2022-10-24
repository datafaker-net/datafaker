package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MountaineeringTest extends BaseFakerTest<BaseFaker> {

    @Test
    void mountaineer() {
        assertThat(faker.mountaineering().mountaineer()).isNotEmpty();
    }
}
