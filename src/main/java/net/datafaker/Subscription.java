package net.datafaker;

/**
 * @since 1.3.0
 */
public class Subscription extends AbstractProvider {

    protected Subscription(Faker faker) {
        super(faker);
    }

    public String plans() {
        return faker.fakeValuesService().resolve("subscription.plans", this);
    }

    public String statuses() {
        return faker.fakeValuesService().resolve("subscription.statuses", this);
    }

    public String paymentMethods() {
        return faker.fakeValuesService().resolve("subscription.payment_methods", this);
    }

    public String subscriptionTerms() {
        return faker.fakeValuesService().resolve("subscription.subscription_terms", this);
    }

    public String paymentTerms() {
        return faker.fakeValuesService().resolve("subscription.payment_terms", this);
    }

}
