package net.datafaker.providers.movie;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DetectiveConanTest extends net.datafaker.AbstractFakerTest {

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
