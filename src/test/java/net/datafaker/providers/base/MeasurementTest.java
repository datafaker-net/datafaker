package net.datafaker.providers.base;

import org.junit.jupiter.api.Nested;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

class MeasurementTest extends BaseFakerTest<BaseFaker> {

    private static Collection<TestSpec> getProviderListTests(Measurement measurement) {
        return List.of(
            TestSpec.of(measurement::height, "measurement.height"),
            TestSpec.of(measurement::length, "measurement.length"),
            TestSpec.of(measurement::volume, "measurement.volume"),
            TestSpec.of(measurement::weight, "measurement.weight"),
            TestSpec.of(measurement::metricLength, "measurement.metric_length"),
            TestSpec.of(measurement::metricHeight, "measurement.metric_height"),
            TestSpec.of(measurement::metricVolume, "measurement.metric_volume"),
            TestSpec.of(measurement::metricWeight, "measurement.metric_weight")
        );
    }

    @Override
    protected Collection<TestSpec> providerListTest() {
        Measurement measurement = faker.measurement();
        return getProviderListTests(measurement);
    }

    @Nested
    class MeasurementInGreekTest extends BaseFakerTest<BaseFaker> {

        @Override
        protected final BaseFaker getFaker() {
            return new BaseFaker(new Locale("el", "GR"));
        }

        @Override
        protected Collection<TestSpec> providerListTest() {
            Measurement measurement = faker.measurement();
            return getProviderListTests(measurement);
        }
    }
}
