package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class OverwatchTest extends AbstractFakerTest {

    @Test
    public void hero() {
        assertThat(faker.overwatch().hero()).matches("^(\\w+\\.?\\s?)+$");
    }

    @Test
    public void location() {
        assertThat(faker.overwatch().location()).matches("^(.+'?:?\\s?)+$");
    }

    @Test
    public void quote() {
        assertFalse(faker.overwatch().quote().isEmpty());
    }
}
