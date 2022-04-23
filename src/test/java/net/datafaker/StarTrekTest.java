package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StarTrekTest extends AbstractFakerTest {

    @Test
    public void character() {
        assertThat(faker.starTrek().character()).matches("^(\\w+-?'?\\.?\\s?)+$");
    }

    @Test
    public void location() {
        assertThat(faker.starTrek().location()).matches("^(\\w+'?\\s?)+$");
    }

    @Test
    public void species() {
        assertThat(faker.starTrek().species()).matches("^(\\w+-?'?\\s?)+$");
    }

    @Test
    public void villain() {
        assertThat(faker.starTrek().villain()).matches("^(\\w+'?\\.?\\s?)+$");
    }

    @Test
    public void klingon() {
        assertThat(faker.starTrek().klingon()).isNotEmpty();
    }
}
