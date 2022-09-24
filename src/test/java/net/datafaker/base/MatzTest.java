package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MatzTest extends BaseFakerTest<BaseFaker> {

    @Test
    void quote() {
        assertThat(faker.matz().quote()).isNotEmpty();
    }
}
