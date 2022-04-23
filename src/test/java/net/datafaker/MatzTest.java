package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MatzTest extends AbstractFakerTest {

    @Test
    public void quote() {
        assertThat(faker.matz().quote()).isNotEmpty();
    }
}
