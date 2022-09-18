package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CryptoCoinTest extends BaseFakerTest {

    @Test
    void coin() {
        assertThat(faker.cryptoCoin().coin()).isNotEmpty();
    }
}
