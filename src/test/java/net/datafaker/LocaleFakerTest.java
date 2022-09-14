package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class LocaleFakerTest extends AbstractFakerTest {

    @RepeatedTest(10)
    void baseLocale() {
        assertThat(faker.localeFaker().baseLocale()).isNotEmpty();
    }

    @RepeatedTest(10)
    void displayName() {
        assertThat(faker.localeFaker().displayName()).isNotEmpty();
    }
}
