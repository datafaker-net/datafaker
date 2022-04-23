package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SizeTest extends AbstractFakerTest {

    @Test
    public void adjective() {
        assertThat(faker.size().adjective()).matches("[a-zA-Z]+(-[a-zA-Z]+)?");
    }
}
