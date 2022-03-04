package net.datafaker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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
        assertThat(fakeValuesGrouping.get("address").equals(addressValues.get("address")), equalTo(true));
        assertThat(fakeValuesGrouping.get("address"), is(notNullValue()));
    }

    @Test
    public void handlesMultipleFakeValues() {
        FakeValues catValues = new FakeValues(Locale.ENGLISH, "cat.yml", "creature");
        fakeValuesGrouping.add(catValues);

        assertThat(fakeValuesGrouping.get("address").equals(addressValues.get("address")), equalTo(true));
        assertThat(fakeValuesGrouping.get("address"), is(notNullValue()));

        assertThat(fakeValuesGrouping.get("creature").equals(catValues.get("creature")), equalTo(true));
        assertThat(fakeValuesGrouping.get("creature"), is(notNullValue()));
    }

}
