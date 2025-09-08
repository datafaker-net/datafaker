package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("removal")
class CurrencyTest extends BaseFakerTest {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Currency currency = faker.currency();
        return List.of(TestSpec.of(currency::name, "currency.name", "[\\w'.\\-() ]+"),
            TestSpec.of(currency::code, "currency.code", "[A-Z]{3}"));
    }
}
