package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CryptoCoinTest extends AbstractFakerTest {

    @Test
    void coin() {
        assertThat(faker.cryptoCoin().coin()).isNotEmpty();
    }
}
