package net.datafaker.providers.base;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZodiacTest extends BaseFakerTest<BaseFaker> {

    @Test
    void testSign() {
        assertThat(faker.zodiac().sign()).isNotEmpty();
    }
}
