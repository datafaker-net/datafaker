package net.datafaker;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class SubscriptionTest extends AbstractFakerTest {

    @Test
    public void plans() {
        assertThat(faker.subscription().plans(), not(is(emptyOrNullString())));
    }

    @Test
    public void statuses() {
        assertThat(faker.subscription().statuses(), not(is(emptyOrNullString())));
    }

    @Test
    public void paymentMethods() {
        assertThat(faker.subscription().paymentMethods(), not(is(emptyOrNullString())));
    }

    @Test
    public void subscriptionTerms() {
        assertThat(faker.subscription().subscriptionTerms(), not(is(emptyOrNullString())));
    }

    @Test
    public void paymentTerms() {
        assertThat(faker.subscription().paymentTerms(), not(is(emptyOrNullString())));
    }

}
