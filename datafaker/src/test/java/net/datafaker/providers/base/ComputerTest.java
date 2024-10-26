package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class ComputerTest extends BaseFakerTest<BaseFaker> {

    Computer computer = faker.computer();

    @Test
    void testOperatingSystem() {
        assertThat(computer.operatingSystem()).isNotEmpty();
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(computer::type, "computer.type"),
                TestSpec.of(computer::platform, "computer.platform"),
                TestSpec.of(computer::linux, "computer.os.linux"),
                TestSpec.of(computer::macos, "computer.os.macos"),
                TestSpec.of(computer::windows, "computer.os.windows"),
                TestSpec.of(computer::brand, "computer.brand"));
    }
}
