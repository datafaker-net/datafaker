package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SuitsTest extends net.datafaker.AbstractFakerTest {

    @Test
    void characters() {
        assertThat(faker.suits().characters()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.suits().quotes()).isNotEmpty();
    }

}
