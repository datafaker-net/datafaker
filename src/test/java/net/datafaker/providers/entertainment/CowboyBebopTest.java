package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CowboyBebopTest extends EntertainmentFakerTest {

    @Test
    void character() {
        assertThat(faker.cowboyBebop().character()).isNotEmpty();
    }

    @Test
    void episode() {
        assertThat(faker.cowboyBebop().episode()).isNotEmpty();
    }

    @Test
    void song() {
        assertThat(faker.cowboyBebop().song()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.cowboyBebop().quote()).isNotEmpty();
    }

}
