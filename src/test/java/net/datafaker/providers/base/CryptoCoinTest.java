package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CryptoCoinTest extends BaseFakerTest<BaseFaker> {

    @Test
    void coin() {
        assertThat(faker.cryptoCoin().coin()).isNotEmpty();
    }
}
