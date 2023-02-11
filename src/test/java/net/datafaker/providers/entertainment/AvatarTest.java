package net.datafaker.providers.entertainment;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class AvatarTest extends EntertainmentFakerTest {

    @RepeatedTest(10)
    void testAvatar() {
        String avatar = getFaker().avatar().image();
        assertThat(avatar).matches("^https://robohash.org/[a-z]{8}.png$");
    }
}
