package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceTest extends AbstractFakerTest {

    @Test
    public void modelName() {
        assertThat(faker.device().modelName()).isNotEmpty();
    }

    @Test
    public void platform() {
        assertThat(faker.device().platform()).isNotEmpty();
    }

    @Test
    public void manufacturer() {
        assertThat(faker.device().manufacturer()).isNotEmpty();
    }

    @Test
    public void serial() {
        assertThat(faker.device().serial()).isNotEmpty();
    }
}
