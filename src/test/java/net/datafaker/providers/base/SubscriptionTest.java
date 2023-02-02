package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class SubscriptionTest extends AbstractBasicProviderTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        return Arrays.asList(TestSpec.of(() -> faker.subscription().plans(), "subscription.plans"),
                TestSpec.of(() -> faker.subscription().statuses(), "subscription.statuses"),
                TestSpec.of(() -> faker.subscription().paymentMethods(), "subscription.payment_methods"),
                TestSpec.of(() -> faker.subscription().subscriptionTerms(), "subscription.subscription_terms"),
                TestSpec.of(() -> faker.subscription().paymentTerms(), "subscription.payment_terms"));
    }

}
