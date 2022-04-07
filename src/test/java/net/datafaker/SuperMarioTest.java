package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SuperMarioTest extends AbstractFakerTest {

    @Test
    public void characters() {
        assertThat(faker.superMario().characters()).isNotEmpty();
    }

    @Test
    public void games() {
        assertThat(faker.superMario().games()).isNotEmpty();
    }
    @Test
    public void locations() {
        assertThat(faker.superMario().locations()).isNotEmpty();
    }

}
