package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Luka Obradovic (luka@vast.com)
 */
class YodaTest extends BaseFakerTest<BaseFaker> {

    @Test
    void quote() {
        assertThat(faker.yoda().quote()).isNotEmpty();
    }
}
