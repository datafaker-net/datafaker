package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NewGirlTest extends EntertainmentFakerTest {

    @Test
    void characters() {
        assertThat(faker.newGirl().characters()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.newGirl().quotes()).isNotEmpty();
    }

}
