package net.datafaker.providers.movie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class HeyArnoldTest extends MovieFakerTest {

    @Test
    void characters() {
        Assertions.assertThat(faker.heyArnold().characters()).isNotEmpty();
    }

    @Test
    void locations() {
        Assertions.assertThat(faker.heyArnold().locations()).isNotEmpty();
    }

    @Test
    void quotes() {
        Assertions.assertThat(faker.heyArnold().quotes()).isNotEmpty();
    }
}
