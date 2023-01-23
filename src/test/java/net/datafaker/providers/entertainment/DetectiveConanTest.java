package net.datafaker.providers.entertainment;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DetectiveConanTest extends EntertainmentFakerTest {

    @Test
    void characters() {
        assertThat(faker.detectiveConan().characters()).isNotEmpty();
    }

    @Test
    void gadgets() {
        assertThat(faker.detectiveConan().gadgets()).isNotEmpty();
    }

    @Test
    void vehicles() {
        assertThat(faker.detectiveConan().vehicles()).isNotEmpty();
    }

}
