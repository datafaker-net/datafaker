package net.datafaker;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class MeasurementTest extends AbstractFakerTest {

    public static final String WORDS = "^[a-z ]+$";

    @RepeatedTest(10)
    void testHeight() {
        assertThat(faker.measurement().height()).matches(WORDS);
    }

    @RepeatedTest(10)
    void testLength() {
        assertThat(faker.measurement().length()).matches(WORDS);
    }

    @RepeatedTest(10)
    void testVolume() {
        assertThat(faker.measurement().volume()).matches(WORDS);
    }

    @RepeatedTest(10)
    void testWeight() {
        assertThat(faker.measurement().weight()).matches(WORDS);
    }

    @RepeatedTest(10)
    void testMetricHeight() {
        assertThat(faker.measurement().metricHeight()).matches(WORDS);
    }

    @RepeatedTest(10)
    void testMetricLength() {
        assertThat(faker.measurement().metricLength()).matches(WORDS);
    }

    @RepeatedTest(10)
    void testMetricVolume() {
        assertThat(faker.measurement().metricVolume()).matches(WORDS);
    }

    @RepeatedTest(10)
    void testMetricWeight() {
        assertThat(faker.measurement().metricWeight()).matches(WORDS);
    }
}
