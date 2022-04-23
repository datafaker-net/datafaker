package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GratefulDeadTest extends AbstractFakerTest {

    @Test
    public void players() {
        assertThat(faker.gratefulDead().players()).isNotEmpty();
    }

    @Test
    public void songs() {
        assertThat(faker.gratefulDead().songs()).isNotEmpty();
    }

}
