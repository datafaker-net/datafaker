package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SpongebobTest extends ShowFakerTest {

    @Test
    void characters() {
        assertThat(faker.spongebob().characters()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.spongebob().quotes()).isNotEmpty();
    }

    @Test
    void episodes() {
        assertThat(faker.spongebob().episodes()).isNotEmpty();
    }

}

