package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Luka Obradovic (luka@vast.com)
 */
class YodaTest extends AbstractFakerTest {

    @Test
    void quote() {
        assertThat(faker.yoda().quote()).isNotEmpty();
    }
}
