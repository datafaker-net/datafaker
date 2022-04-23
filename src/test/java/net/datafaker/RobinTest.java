package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RobinTest extends AbstractFakerTest {

    @Test
    public void quote() {
        assertThat(faker.robin().quote()).matches("^(\\w+\\.?-?'?\\s?)+(\\(?)?(\\w+\\s?\\.?)+(\\))?$");
    }
}
