package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class BrandTest extends AbstractBasicProviderTest<BaseFaker> {

    @Test
    void testSport() {
        assertThat(faker.brand().sport()).isNotEmpty();
    }

    @Test
    void testCar() {
        assertThat(faker.brand().car()).isNotEmpty();
    }

    @Test
    void testWatch() {
        assertThat(faker.brand().watch()).isNotEmpty();
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(
            TestSpec.of(() -> faker.brand().sport(), "brand.sport"),
            TestSpec.of(() -> faker.brand().car(), "vehicle.makes"),
            TestSpec.of(() -> faker.brand().watch(), "brand.watch")
        );
    }
}
