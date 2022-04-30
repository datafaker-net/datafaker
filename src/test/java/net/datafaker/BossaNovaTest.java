package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class BossaNovaTest extends AbstractFakerTest {

    @RepeatedTest(10)
    void artists() {
        assertThat(faker.bossaNova().artist()).matches("[A-Za-z .-]+");
    }

    @RepeatedTest(10)
    void songs() {
        assertThat(faker.bossaNova().song()).isNotEmpty();
    }
}
