package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class SubscriptionTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Subscription subscription = faker.subscription();
        return List.of(TestSpec.of(subscription::plans, "subscription.plans"),
                TestSpec.of(subscription::statuses, "subscription.statuses"),
                TestSpec.of(subscription::paymentMethods, "subscription.payment_methods"),
                TestSpec.of(subscription::subscriptionTerms, "subscription.subscription_terms"),
                TestSpec.of(subscription::paymentTerms, "subscription.payment_terms"));
    }

}
