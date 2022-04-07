package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

public class AvatarTest extends AbstractFakerTest {

    @RepeatedTest(10)
    public void testAvatar() {
        String avatar = faker.avatar().image();
        assertThat(avatar).matches("^https://s3.amazonaws\\.com/uifaces/faces/twitter/[a-zA-Z0-9_]+/128\\.jpg$");
    }
}
