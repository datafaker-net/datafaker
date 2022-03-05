package net.datafaker;

/**
 * @since 1.3.0
 */
public class Subscription {

    private final Faker faker;

    protected Subscription(Faker faker) {
        this.faker = faker;
    }

    public String plans() {
        return faker.fakeValuesService().resolve("subscription.plans", this, faker);
    }

    public String statuses() {
        return faker.fakeValuesService().resolve("subscription.statuses", this, faker);
    }

    public String paymentMethods() {
        return faker.fakeValuesService().resolve("subscription.payment_methods", this, faker);
    }

    public String subscriptionTerms() {
        return faker.fakeValuesService().resolve("subscription.subscription_terms", this, faker);
    }

    public String paymentTerms() {
        return faker.fakeValuesService().resolve("subscription.payment_terms", this, faker);
    }

}
