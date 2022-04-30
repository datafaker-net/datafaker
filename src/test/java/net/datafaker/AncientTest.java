package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AncientTest extends AbstractFakerTest {

    @Test
    void god() {
        assertThat(faker.ancient().god()).matches("\\w+");
    }

    @Test
    void primordial() {
        assertThat(faker.ancient().primordial()).matches("\\w+");
    }

    @Test
    void titan() {
        assertThat(faker.ancient().titan()).matches("\\w+");
    }

    @Test
    void hero() {
        assertThat(faker.ancient().hero()).matches("(?U)\\w+");
    }

}
