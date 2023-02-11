package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

class BusinessTest extends AbstractBasicProviderTest<BaseFaker> {

    @Test
    void creditCardNumber() {
        assertThat(faker.business().creditCardNumber()).isNotEmpty();
    }

    @Test
    void creditCardExpiry() {
        // Given / When
        String date = faker.business().creditCardExpiry();
        // Then
        assertThat(date).isNotEmpty();
        assertThat(date).matches("\\d{4}-\\d{2}-\\d{2}");
    }

    @Test
    void securityCode() {
        assertThat(faker.business().securityCode()).isNotEmpty();
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.business().creditCardType(), "business.credit_card_types"));
    }
}
