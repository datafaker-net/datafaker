package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DogTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.dog().name()).matches("[A-Za-z ]+");
    }

    @Test
    public void breed() {
        assertThat(faker.dog().breed()).matches("[A-Za-z ]+");
    }

    @Test
    public void sound() {
        assertThat(faker.dog().sound()).matches("[A-Za-z ]+");
    }

    @Test
    public void memePhrase() {
        assertThat(faker.dog().memePhrase()).matches("[A-Za-z0-9'/ ]+");
    }

    @Test
    public void age() {
        assertThat(faker.dog().age()).matches("[A-Za-z ]+");
    }

    @Test
    public void gender() {
        assertThat(faker.dog().gender()).matches("[A-Za-z ]+");
    }

    @Test
    public void coatLength() {
        assertThat(faker.dog().coatLength()).matches("[A-Za-z ]+");
    }

    @Test
    public void size() {
        assertThat(faker.dog().size()).matches("[A-Za-z ]+");
    }
}
