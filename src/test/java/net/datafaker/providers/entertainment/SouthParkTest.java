package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SouthParkTest extends EntertainmentFakerTest {

    @Test
    void characters() {
        assertThat(faker.southPark().characters()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.southPark().quotes()).isNotEmpty();
    }

}
