package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NewGirlTest extends ShowFakerTest {

    @Test
    void characters() {
        assertThat(faker.newGirl().characters()).isNotEmpty();
    }

    @Test
    void quotes() {
        assertThat(faker.newGirl().quotes()).isNotEmpty();
    }

}
