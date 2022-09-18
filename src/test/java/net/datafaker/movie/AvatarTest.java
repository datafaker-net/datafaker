package net.datafaker.movie;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class AvatarTest extends MovieFakerTest {

    @RepeatedTest(10)
    void testAvatar() {
        String avatar = faker.avatar().image();
        assertThat(avatar).matches("^https://robohash.org/[a-z]{8}.png$");
    }
}
