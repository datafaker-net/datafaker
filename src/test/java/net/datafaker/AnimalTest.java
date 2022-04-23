package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalTest extends AbstractFakerTest {

    @Test
    public void name() {
        assertThat(faker.animal().name()).matches("[A-Za-z ]+");
    }

}
