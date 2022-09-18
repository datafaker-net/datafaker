package net.datafaker.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ComputerTest extends BaseFakerTest {

    @Test
    void testType() {
        assertThat(faker.computer().type()).isNotEmpty();
    }

    @Test
    void testPlatform() {
        assertThat(faker.computer().platform()).isNotEmpty();
    }

    @Test
    void testOperatingSystem() {
        assertThat(faker.computer().operatingSystem()).isNotEmpty();
    }

    @Test
    void testLinux() {
        assertThat(faker.computer().linux()).isNotEmpty();
    }

    @Test
    void testMacos() {
        assertThat(faker.computer().macos()).isNotEmpty();
    }

    @Test
    void testWindows() {
        assertThat(faker.computer().windows()).isNotEmpty();
    }
}
