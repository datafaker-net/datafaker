package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MountaineeringTest extends AbstractFakerTest {

    @Test
    public void mountaineer() {
        assertThat(faker.mountaineering().mountaineer()).isNotEmpty();
    }
}

