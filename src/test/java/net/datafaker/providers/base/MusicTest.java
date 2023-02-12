package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class MusicTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Music music = faker.music();
        return List.of(TestSpec.of(music::instrument, "music.instruments", "\\w+ ?\\w+"));
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
