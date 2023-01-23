package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TheKingkillerChronicleTest extends EntertainmentFakerTest {

    @Test
    void book() {
        assertThat(faker.theKingkillerChronicle().book()).isNotEmpty();
    }

    @Test
    void character() {
        assertThat(faker.theKingkillerChronicle().character()).isNotEmpty();
    }

    @Test
    void creature() {
        assertThat(faker.theKingkillerChronicle().creature()).isNotEmpty();
    }

    @Test
    void location() {
        assertThat(faker.theKingkillerChronicle().location()).isNotEmpty();
    }
}
