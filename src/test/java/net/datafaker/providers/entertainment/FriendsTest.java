package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FriendsTest extends EntertainmentFakerTest {

    @Test
    void character() {
        assertThat(faker.friends().character()).matches("[A-Za-z .,]+");
    }

    @Test
    void location() {
        assertThat(faker.friends().location()).matches("[\\w.', ]+");
    }

    @Test
    void quote() {
        assertThat(faker.friends().quote()).isNotEmpty();
    }
}
