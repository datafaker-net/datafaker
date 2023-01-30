package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TheExpanseTest extends EntertainmentFakerTest {

    @Test
    void characters() {
        assertThat(faker.theExpanse().characters()).isNotEmpty();
    }

    @Test
    void locations() {
        assertThat(faker.theExpanse().locations()).isNotEmpty();
    }

    @Test
    void ships() {
        assertThat(faker.theExpanse().ships()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.theExpanse().quotes()).isNotEmpty();
    }

}
