package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CatTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.cat().name()).matches("[A-Za-z'() 0-9-]+");
    }

    @Test
    public void breed() {
        assertThat(faker.cat().breed()).matches("[A-Za-z'() 0-9-,]+");
    }

    @Test
    public void registry() {
        assertThat(faker.cat().registry()).matches("[A-Za-zé'() 0-9-]+");
    }
}
