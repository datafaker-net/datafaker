package net.datafaker.providers.base;

/**
 * @since 1.3.0
 */
public class Subscription extends AbstractProvider<BaseProviders> {

    protected Subscription(BaseProviders faker) {
        super(faker);
    }

    public String plans() {
        return resolve("subscription.plans");
    }

    public String statuses() {
        return resolve("subscription.statuses");
    }

    public String paymentMethods() {
        return resolve("subscription.payment_methods");
    }

    public String subscriptionTerms() {
        return resolve("subscription.subscription_terms");
    }

    public String paymentTerms() {
        return resolve("subscription.payment_terms");
    }

}
