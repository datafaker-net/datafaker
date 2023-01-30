package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SwordArtOnlineTest extends EntertainmentFakerTest {

    @Test
    void realName() {
        assertThat(faker.swordArtOnline().realName()).isNotEmpty();
    }

    @Test
    void gameName() {
        assertThat(faker.swordArtOnline().gameName()).isNotEmpty();
    }

    @Test
    void location() {
        assertThat(faker.swordArtOnline().location()).isNotEmpty();
    }

    @Test
    void item() {
        assertThat(faker.swordArtOnline().item()).isNotEmpty();
    }

}
