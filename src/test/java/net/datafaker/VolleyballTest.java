package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VolleyballTest extends AbstractFakerTest {

    @Test
    public void team() {
        assertThat(faker.volleyball().team()).isNotEmpty();
    }

    @Test
    public void player() {
        assertThat(faker.volleyball().player()).isNotEmpty();
    }

    @Test
    public void coach() {
        assertThat(faker.volleyball().coach()).isNotEmpty();
    }

    @Test
    public void position() {
        assertThat(faker.volleyball().position()).isNotEmpty();
    }

    @Test
    public void formation() {
        assertThat(faker.volleyball().formation()).isNotEmpty();
    }

}
