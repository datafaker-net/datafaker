package net.datafaker.providers.movie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class StarTrekTest extends MovieFakerTest {

    @Test
    void character() {
        Assertions.assertThat(faker.starTrek().character()).matches("^(\\w+-?'?\\.?\\s?)+$");
    }

    @Test
    void location() {
        Assertions.assertThat(faker.starTrek().location()).matches("^(\\w+'?\\s?)+$");
    }

    @Test
    void species() {
        Assertions.assertThat(faker.starTrek().species()).matches("^(\\w+-?'?\\s?)+$");
    }

    @Test
    void villain() {
        Assertions.assertThat(faker.starTrek().villain()).matches("^(\\w+'?\\.?\\s?)+$");
    }

    @Test
    void klingon() {
        Assertions.assertThat(faker.starTrek().klingon()).isNotEmpty();
    }
}
