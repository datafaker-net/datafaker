package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SupernaturalTest extends EntertainmentFakerTest {

    @Test
    void character() {
        assertThat(faker.supernatural().character()).isNotEmpty();
    }

    @Test
    void creature() {
        assertThat(faker.supernatural().creature()).isNotEmpty();
    }

    @Test
    void weapon() {
        assertThat(faker.supernatural().weapon()).isNotEmpty();
    }

}

