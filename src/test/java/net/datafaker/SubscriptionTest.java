package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SubscriptionTest extends AbstractFakerTest {

    @Test
    void plans() {
        assertThat(faker.subscription().plans()).isNotEmpty();
    }

    @Test
    void statuses() {
        assertThat(faker.subscription().statuses()).isNotEmpty();
    }

    @Test
    void paymentMethods() {
        assertThat(faker.subscription().paymentMethods()).isNotEmpty();
    }

    @Test
    void subscriptionTerms() {
        assertThat(faker.subscription().subscriptionTerms()).isNotEmpty();
    }

    @Test
    void paymentTerms() {
        assertThat(faker.subscription().paymentTerms()).isNotEmpty();
    }

}
