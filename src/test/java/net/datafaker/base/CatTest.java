package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CatTest extends AbstractBaseFakerTest {

    @Test
    void name() {
        assertThat(faker.cat().name()).matches("[A-Za-z'() 0-9-]+");
    }

    @Test
    void breed() {
        assertThat(faker.cat().breed()).matches("[A-Za-z'() 0-9-,]+");
    }

    @Test
    void registry() {
        assertThat(faker.cat().registry()).matches("[A-Za-zé'() 0-9-]+");
    }
}
