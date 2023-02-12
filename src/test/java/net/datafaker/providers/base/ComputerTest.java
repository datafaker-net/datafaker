package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class ComputerTest extends AbstractBasicProviderTest<BaseFaker> {

    @Test
    void testOperatingSystem() {
        assertThat(faker.computer().operatingSystem()).isNotEmpty();
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(() -> faker.computer().type(), "computer.type"),
                TestSpec.of(() -> faker.computer().platform(), "computer.platform"),
                TestSpec.of(() -> faker.computer().linux(), "computer.os.linux"),
                TestSpec.of(() -> faker.computer().macos(), "computer.os.macos"),
                TestSpec.of(() -> faker.computer().windows(), "computer.os.windows"),
                TestSpec.of(() -> faker.computer().brand(), "computer.brand"));
    }
}
