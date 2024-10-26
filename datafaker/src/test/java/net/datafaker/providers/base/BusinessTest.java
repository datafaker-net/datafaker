package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

class BusinessTest extends BaseFakerTest<BaseFaker> {

    private final Business business = faker.business();

    @Test
    void creditCardNumber() {
        assertThat(business.creditCardNumber()).isNotEmpty();
    }

    @Test
    void creditCardExpiry() {
        // Given / When
        String date = business.creditCardExpiry();
        // Then
        assertThat(date).isNotEmpty();
        assertThat(date).matches("\\d{4}-\\d{2}-\\d{2}");
    }

    @Test
    void securityCode() {
        assertThat(business.securityCode()).isNotEmpty();
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return List.of(TestSpec.of(business::creditCardType, "business.credit_card_types"));
    }
}
