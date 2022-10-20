package net.datafaker.providers.movie;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class AvatarTest extends MovieFakerTest {

    @RepeatedTest(10)
    void avatar() {
        String avatar = faker.avatar().image();
        assertThat(avatar).matches("^https://robohash.org/[a-z]{8}.png$");
    }
}
