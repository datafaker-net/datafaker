package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocaleFakerTest extends AbstractFakerTest {

    @Test
    void baseLocale() {
        assertThat(faker.localeFaker().baseLocale()).isNotEmpty();
    }

    @Test
    void displayName() {
        assertThat(faker.localeFaker().displayName()).isNotEmpty();
    }
}
