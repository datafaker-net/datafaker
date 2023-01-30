package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EntertainmentTest extends EntertainmentFakerTest {

    @Test
    void adultMusical() {
        assertThat(faker.show().adultMusical()).isNotEmpty();
    }

    @Test
    void play() {
        assertThat(faker.show().play()).isNotEmpty();
    }

    @Test
    void kidsMusical() {
        assertThat(faker.show().kidsMusical()).isNotEmpty();
    }

}
