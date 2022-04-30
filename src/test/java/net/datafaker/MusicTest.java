package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MusicTest extends AbstractFakerTest {

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

    @Test
    void genre() {
        assertThat(faker.music().genre()).matches("[[ -]?\\w+]+");
    }
}
