package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SouthParkTest extends net.datafaker.AbstractFakerTest {

    @Test
    void characters() {
        assertThat(faker.southPark().characters()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.southPark().quotes()).isNotEmpty();
    }

}
