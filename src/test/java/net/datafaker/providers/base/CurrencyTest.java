package net.datafaker.providers.base;

import java.util.Arrays;
import java.util.Collection;

class CurrencyTest extends BaseFakerTest<BaseFaker> {

    @Override
    protected Collection<TestSpec> providerListTest() {
        Currency currency = faker.currency();
        return Arrays.asList(TestSpec.of(currency::name, "currency.name", "[\\w'.\\-() ]+"),
            TestSpec.of(currency::code, "currency.code", "[A-Z]{3}"));
    }
}
