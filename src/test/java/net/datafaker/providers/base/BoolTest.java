package net.datafaker.providers.base;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoolTest extends BaseFakerTest<BaseFaker> {

    @RepeatedTest(100)
    void testBool() {
        assertThat(faker.bool().bool()).isIn(true, false);
    }

    @RepeatedTest(100)
    void testBoolFalse() {
        assertThat(faker.bool().bool(0.0)).isFalse();
    }

    @RepeatedTest(100)
    void testBoolTrue() {
        assertThat(faker.bool().bool(1.0)).isTrue();
    }

    @RepeatedTest(100)
    void testBoolWithProbability() {
        int size = 1000;
        Bool bool = faker.bool();
        double[] probabilities = {0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1};
        for (double probability : probabilities) {
            List<Boolean> randomBooleans = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                randomBooleans.add(bool.bool(probability));
            }

            int numberOfTrues = 0;
            for (Boolean b : randomBooleans) {
                if (b) {
                    numberOfTrues++;
                }
            }

            double actual = 1.0 * numberOfTrues / size;
            assertThat(actual).isCloseTo(probability, Offset.offset(0.08));
        }
    }

    @ParameterizedTest()
    @ValueSource(doubles = {-1.0, 1.5, 10.0, -0.015})
    void testBoolException(double probability) {
        assertThatThrownBy(
            () -> faker.bool()
                .bool(probability)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Probability parameter should be between [0.0, 1.0]: " + probability);
    }

}
