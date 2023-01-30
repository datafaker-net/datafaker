package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

class ApplianceTest extends AbstractBasicProviderTest<BaseFaker> {

    @Test
    void brand() {
        assertThat(faker.appliance().brand()).matches("[A-Za-z .-]+");
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.appliance().equipment(), "appliance.equipment"));
    }
}
