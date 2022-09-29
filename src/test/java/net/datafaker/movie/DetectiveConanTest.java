package net.datafaker.movie;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DetectiveConanTest extends net.datafaker.AbstractFakerTest {

    @Test
    public void characters() {
        assertThat(faker.detectiveConan().characters()).isNotEmpty();
    }

    @Test
    public void gadgets() {
        assertThat(faker.detectiveConan().gadgets()).isNotEmpty();
    }

    @Test
    public void vehicles() {
        assertThat(faker.detectiveConan().vehicles()).isNotEmpty();
    }

}
