package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RobinTest extends AbstractFakerTest {

    @Test
    void quote() {
        assertThat(faker.robin().quote()).matches("^(\\w+\\.?-?'?\\s?)+(\\(?)?(\\w+\\s?\\.?)+(\\))?$");
    }
}
