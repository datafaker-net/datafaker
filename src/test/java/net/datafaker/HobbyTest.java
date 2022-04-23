package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HobbyTest extends AbstractFakerTest {

    @Test
    public void activity() {
        assertThat(faker.hobby().activity()).isNotEmpty();
    }
}
