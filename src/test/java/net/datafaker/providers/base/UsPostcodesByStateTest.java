package net.datafaker.providers.base;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

class UsPostcodesByStateTest extends ProviderListTest<BaseFaker> {
    private static final BaseFaker US_FAKER = new BaseFaker(new Locale("en", "US"));

    private static final List<String> STATES = List.of(
        "AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS",
        "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND",
        "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");

    @Override
    protected BaseFaker getFaker() {
        return US_FAKER;
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        return STATES.stream()
            .map(state -> TestSpec.of(() -> US_FAKER.address().zipCodeByState(state), "address.postcode_by_state." + state, "[0-9]{5}"))
            .toList();
    }
}
