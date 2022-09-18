package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SizeTest extends AbstractBaseFakerTest {

    @Test
    void adjective() {
        assertThat(faker.size().adjective()).matches("[a-zA-Z]+(-[a-zA-Z]+)?");
    }
}
