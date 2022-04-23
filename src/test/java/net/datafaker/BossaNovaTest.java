package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

public class BossaNovaTest extends AbstractFakerTest {

    @RepeatedTest(10)
    public void artists() {
        assertThat(faker.bossaNova().artist()).matches("[A-Za-z .-]+");
    }

    @RepeatedTest(10)
    public void songs() {
        assertThat(faker.bossaNova().song()).isNotEmpty();
    }
}
