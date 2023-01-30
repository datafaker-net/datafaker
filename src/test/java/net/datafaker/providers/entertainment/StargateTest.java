package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StargateTest extends EntertainmentFakerTest {

    @Test
    void characters() {
        assertThat(faker.stargate().characters()).isNotEmpty();
    }

    @Test
    void planets() {
        assertThat(faker.stargate().planets()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.stargate().quotes()).isNotEmpty();
    }
}

