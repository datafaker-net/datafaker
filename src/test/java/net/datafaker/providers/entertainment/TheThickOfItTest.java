package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TheThickOfItTest extends EntertainmentFakerTest {

    @Test
    void characters() {
        assertThat(faker.theThickOfIt().characters()).isNotEmpty();
    }

    @Test
    void positions() {
        assertThat(faker.theThickOfIt().positions()).isNotEmpty();
    }

    @Test
    void departments() {
        assertThat(faker.theThickOfIt().departments()).isNotEmpty();
    }

}
