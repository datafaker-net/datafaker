package net.datafaker.providers.entertainment;

import net.datafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class AvatarTest {

    @RepeatedTest(10)
    void testAvatar() {
        String avatar = new Faker().avatar().image();
        assertThat(avatar).matches("^https://robohash.org/[a-z]{8}.png$");
    }
}
