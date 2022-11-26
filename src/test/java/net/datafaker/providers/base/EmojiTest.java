package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmojiTest extends BaseFakerTest<BaseFaker> {

    @Test
    void smiley() {
        assertThat(faker.emoji().smiley()).isNotEmpty();
    }

    @Test
    void cat() {
        assertThat(faker.emoji().cat()).isNotEmpty();
    }

}
