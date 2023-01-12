package net.datafaker.providers.videogame;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VideoGameTest extends VideoGameFakerTest {

    @Test
    void title() {
        assertThat(faker.videoGame().title()).isNotEmpty();
    }

    @Test
    void genre() {
        assertThat(faker.videoGame().genre()).isNotEmpty();
    }

    @Test
    void platform() {
        assertThat(faker.videoGame().platform()).isNotEmpty();
    }

}
