package net.datafaker.providers.base;

/**
 * Provides financial terms.
 *
 * @since 2.4.0
 */
public class FinancialTerms extends AbstractProvider<BaseProviders> {

    public FinancialTerms(BaseProviders faker) {
        super(faker);
    }

    public String noun() {
        return noun(faker.options().option(FinancialTerms.Category.class));
    }

    public String noun(Category category) {
        return resolve("financial_terms." + category.getName() + ".nouns");
    }

    public String verb() {
        return verb(faker.options().option(FinancialTerms.Category.class));
    }

    public String verb(Category category) {
        return resolve("financial_terms." + category.getName() + ".verbs");
    }

    public String adjective() {
        return adjective(faker.options().option(FinancialTerms.Category.class));
    }

    public String adjective(Category category) {
        return resolve("financial_terms." + category.getName() + ".adjectives");
    }

    public enum Category {
        ACCOUNT_MANAGEMENT("account_management"),
        TRANSACTIONS("transactions"),
        PAYMENTS("payments"),
        TRANSFERS("transfers"),
        INVESTMENTS("investments"),
        LOANS("loans"),
        ALERTS_AND_NOTIFICATIONS("alerts_and_notifications"),
        CUSTOMER_SUPPORT("customer_support"),
        SECURITY("security"),
        REGULATORY_COMPLIANCE("regulatory_compliance"),
        MARKET_DATA("market_data"),
        USER_ACTIVITY("user_activity"),
        BANKING("banking"),
        CARDS("cards"),
        CRYPTOCURRENCY("cryptocurrency"),
        INSURANCE("insurance"),
        REWARDS_AND_LOYALTY("rewards_and_loyalty"),
        MISCELLANEOUS("miscellaneous");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
