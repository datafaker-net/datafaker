package net.datafaker.providers.show;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StrangerThingsTest extends ShowFakerTest {

    @Test
    void character() {
        assertThat(faker.strangerThings().character()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.strangerThings().quote()).isNotEmpty();
    }

}
