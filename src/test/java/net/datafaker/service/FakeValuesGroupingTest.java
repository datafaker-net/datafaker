package net.datafaker.service;

import org.junit.jupiter.api.Test;

import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

class FakeValuesGroupingTest {

    private final FakeValues addressValues = FakeValues.of(FakeValuesContext.of(ENGLISH, "address.yml", "address"));
    private final FakeValuesGrouping fakeValuesGrouping = new FakeValuesGrouping(addressValues);

    @Test
    void handlesOneFakeValue() {
        assertThat(fakeValuesGrouping.get("address")).isEqualTo(addressValues.get("address"))
            .isNotNull();
    }

    @Test
    void handlesMultipleFakeValues() {
        FakeValues catValues = FakeValues.of(FakeValuesContext.of(ENGLISH, "cat.yml", "creature"));
        fakeValuesGrouping.add(catValues);

        assertThat(fakeValuesGrouping.get("address")).isEqualTo(addressValues.get("address"))
            .isNotNull();

        assertThat(fakeValuesGrouping.get("creature")).isEqualTo(catValues.get("creature"))
            .isNotNull();
    }

}
