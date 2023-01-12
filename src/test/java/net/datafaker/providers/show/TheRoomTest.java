package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TheRoomTest extends ShowFakerTest {

    @Test
    void actors() {
        assertThat(faker.theRoom().actors()).isNotEmpty();
    }

    @Test
    void characters() {
        assertThat(faker.theRoom().characters()).isNotEmpty();
    }

    @Test
    void locations() {
        assertThat(faker.theRoom().locations()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.theRoom().quotes()).isNotEmpty();
    }

}
