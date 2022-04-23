package net.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscriptionTest extends AbstractFakerTest {

    @Test
    public void plans() {
        assertThat(faker.subscription().plans()).isNotEmpty();
    }

    @Test
    public void statuses() {
        assertThat(faker.subscription().statuses()).isNotEmpty();
    }

    @Test
    public void paymentMethods() {
        assertThat(faker.subscription().paymentMethods()).isNotEmpty();
    }

    @Test
    public void subscriptionTerms() {
        assertThat(faker.subscription().subscriptionTerms()).isNotEmpty();
    }

    @Test
    public void paymentTerms() {
        assertThat(faker.subscription().paymentTerms()).isNotEmpty();
    }

}
