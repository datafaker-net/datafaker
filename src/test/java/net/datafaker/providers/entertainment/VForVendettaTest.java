package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VForVendettaTest extends EntertainmentFakerTest {

    @Test
    void characters() {
        assertThat(faker.vForVendetta().characters()).isNotEmpty();
    }

    @Test
    void speeches() {
        assertThat(faker.vForVendetta().speeches()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.vForVendetta().quotes()).isNotEmpty();
    }

}
