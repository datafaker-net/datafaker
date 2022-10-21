package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceTest extends BaseFakerTest<BaseFaker> {

    @Test
    void modelName() {
        assertThat(faker.device().modelName()).isNotEmpty();
    }

    @Test
    void platform() {
        assertThat(faker.device().platform()).isNotEmpty();
    }

    @Test
    void manufacturer() {
        assertThat(faker.device().manufacturer()).isNotEmpty();
    }

    @Test
    void serial() {
        assertThat(faker.device().serial()).isNotEmpty();
    }
}
