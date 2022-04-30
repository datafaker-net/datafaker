package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MatzTest extends AbstractFakerTest {

    @Test
    void quote() {
        assertThat(faker.matz().quote()).isNotEmpty();
    }
}
