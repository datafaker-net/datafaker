package net.datafaker.providers.base;

import java.util.List;
import java.util.Collection;

class MeasurementTest extends BaseFakerTest<BaseFaker> {

    public static final String WORDS = "^[a-z ]+$";

    @Override
    protected Collection<TestSpec> providerListTest() {
        Measurement measurement = faker.measurement();
        return List.of(TestSpec.of(measurement::height, "measurement.height"),
            TestSpec.of(measurement::length, "measurement.length"),
            TestSpec.of(measurement::volume, "measurement.volume"),
            TestSpec.of(measurement::weight, "measurement.weight"),
            TestSpec.of(measurement::metricLength, "measurement.metric_length"),
            TestSpec.of(measurement::metricHeight, "measurement.metric_height"),
            TestSpec.of(measurement::metricVolume, "measurement.metric_volume"),
            TestSpec.of(measurement::metricWeight, "measurement.metric_weight"));
    }
}
