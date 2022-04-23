package net.datafaker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeValuesGroupingTest {

    private FakeValuesGrouping fakeValuesGrouping;
    private FakeValues addressValues;

    @BeforeEach
    public void before() {
        fakeValuesGrouping = new FakeValuesGrouping();
        addressValues = new FakeValues(Locale.ENGLISH, "address.yml", "address");
        fakeValuesGrouping.add(addressValues);
    }

    @Test
    public void handlesOneFakeValue() {
        assertThat(fakeValuesGrouping.get("address")).isEqualTo(addressValues.get("address"));
        assertThat(fakeValuesGrouping.get("address")).isNotNull();
    }

    @Test
    public void handlesMultipleFakeValues() {
        FakeValues catValues = new FakeValues(Locale.ENGLISH, "cat.yml", "creature");
        fakeValuesGrouping.add(catValues);

        assertThat(fakeValuesGrouping.get("address")).isEqualTo(addressValues.get("address"));
        assertThat(fakeValuesGrouping.get("address")).isNotNull();

        assertThat(fakeValuesGrouping.get("creature")).isEqualTo(catValues.get("creature"));
        assertThat(fakeValuesGrouping.get("creature")).isNotNull();
    }

}
