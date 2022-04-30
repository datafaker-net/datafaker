package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OverwatchTest extends AbstractFakerTest {

    @Test
    void hero() {
        assertThat(faker.overwatch().hero()).matches("^(\\w+\\.?\\s?)+$");
    }

    @Test
    void location() {
        assertThat(faker.overwatch().location()).matches("^(.+'?:?\\s?)+$");
    }

    @Test
    void quote() {
        assertThat(faker.overwatch().quote()).isNotEmpty();
    }
}
