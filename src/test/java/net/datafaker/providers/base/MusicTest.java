package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MusicTest extends BaseFakerTest<BaseFaker> {

    @Test
    void instrument() {
        assertThat(faker.music().instrument()).matches("\\w+ ?\\w+");
    }

    @Test
    void key() {
        assertThat(faker.music().key()).matches("([A-Z])+([b#])?");
    }

    @Test
    void chord() {
        assertThat(faker.music().chord()).matches("([A-Z])+([b#])?+(-?[a-zA-Z0-9]{0,4})");
    }

    @RepeatedTest(100)
    void genre() {
        assertThat(faker.music().genre()).matches("[ -?\\w+]+");
    }
}
