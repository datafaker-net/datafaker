package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TheVentureBrosTest extends EntertainmentFakerTest {

    @Test
    void character() {
        assertThat(faker.theVentureBros().character()).isNotEmpty();
    }

    @Test
    void organization() {
        assertThat(faker.theVentureBros().organization()).isNotEmpty();
    }

    @Test
    void vehicle() {
        assertThat(faker.theVentureBros().vehicle()).isNotEmpty();
    }

    @Test
    void quote() {
        assertThat(faker.theVentureBros().quote()).isNotEmpty();
    }

}
