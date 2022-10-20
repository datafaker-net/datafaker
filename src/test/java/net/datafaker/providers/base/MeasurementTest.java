package net.datafaker.providers.base;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class MeasurementTest extends BaseFakerTest<BaseFaker> {

    public static final String WORDS = "^[a-z ]+$";

    @RepeatedTest(10)
    void height() {
        assertThat(faker.measurement().height()).matches(WORDS);
    }

    @RepeatedTest(10)
    void length() {
        assertThat(faker.measurement().length()).matches(WORDS);
    }

    @RepeatedTest(10)
    void volume() {
        assertThat(faker.measurement().volume()).matches(WORDS);
    }

    @RepeatedTest(10)
    void weight() {
        assertThat(faker.measurement().weight()).matches(WORDS);
    }

    @RepeatedTest(10)
    void metricHeight() {
        assertThat(faker.measurement().metricHeight()).matches(WORDS);
    }

    @RepeatedTest(10)
    void metricLength() {
        assertThat(faker.measurement().metricLength()).matches(WORDS);
    }

    @RepeatedTest(10)
    void metricVolume() {
        assertThat(faker.measurement().metricVolume()).matches(WORDS);
    }

    @RepeatedTest(10)
    void metricWeight() {
        assertThat(faker.measurement().metricWeight()).matches(WORDS);
    }
}
