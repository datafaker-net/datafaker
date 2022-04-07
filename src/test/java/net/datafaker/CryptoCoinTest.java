package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CryptoCoinTest extends AbstractFakerTest {

    @Test
    public void coin() {
        assertThat(faker.cryptoCoin().coin()).isNotEmpty();
    }
}
