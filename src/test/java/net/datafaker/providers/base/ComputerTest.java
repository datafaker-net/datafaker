package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ComputerTest extends BaseFakerTest<BaseFaker> {

    @Test
    void type() {
        assertThat(faker.computer().type()).isNotEmpty();
    }

    @Test
    void platform() {
        assertThat(faker.computer().platform()).isNotEmpty();
    }

    @Test
    void operatingSystem() {
        assertThat(faker.computer().operatingSystem()).isNotEmpty();
    }

    @Test
    void linux() {
        assertThat(faker.computer().linux()).isNotEmpty();
    }

    @Test
    void macos() {
        assertThat(faker.computer().macos()).isNotEmpty();
    }

    @Test
    void windows() {
        assertThat(faker.computer().windows()).isNotEmpty();
    }
}
