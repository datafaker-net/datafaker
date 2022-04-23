package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PrincessBrideTest extends AbstractFakerTest {
    @Test
    public void character() {
        assertThat(faker.princessBride().character()).matches("[A-Za-z .-]+");
    }

    @Test
    public void quote() {
        assertThat(faker.princessBride().quote()).isNotEmpty();
    }
}
