package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AncientTest extends AbstractFakerTest {

    @Test
    public void god() {
        assertThat(faker.ancient().god()).matches("\\w+");
    }

    @Test
    public void primordial() {
        assertThat(faker.ancient().primordial()).matches("\\w+");
    }

    @Test
    public void titan() {
        assertThat(faker.ancient().titan()).matches("\\w+");
    }

    @Test
    public void hero() {
        assertThat(faker.ancient().hero()).matches("(?U)\\w+");
    }

}
