package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GenderTest extends AbstractFakerTest {

    @Test
    public void types() {
        assertThat(faker.gender().types()).matches("(\\w+ ?){1,2}");
    }

    @Test
    public void binaryTypes() {
        assertThat(faker.gender().binaryTypes()).matches("[A-Za-z ]+");
    }

    @Test
    public void shortBinaryTypes() {
        assertThat(faker.gender().shortBinaryTypes()).matches("[fm]");
    }

}
