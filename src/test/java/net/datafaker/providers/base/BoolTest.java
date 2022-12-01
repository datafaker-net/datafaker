package net.datafaker.providers.base;

import net.datafaker.Faker;
import net.datafaker.configuration.ProbabilityConfig;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BoolTest extends BaseFakerTest<BaseFaker> {
    @RepeatedTest(100)
    void testBool() {
        assertThat(faker.bool().bool()).isIn(true, false);
    }

    @RepeatedTest(100)
    void testBoolFalse() {
        Faker fakerWithProbabilityConfig =
            new Faker().withProbabilityConfig(new ProbabilityConfig().with(true, 0.0));
        assertThat(fakerWithProbabilityConfig.bool().bool()).isFalse();
    }

    @RepeatedTest(100)
    void testBoolTrue() {
        Faker fakerWithProbabilityConfig =
            new Faker().withProbabilityConfig(new ProbabilityConfig().with(true, 1.0));
        assertThat(fakerWithProbabilityConfig.bool().bool()).isTrue();
    }

    @RepeatedTest(100)
    void testBoolWithProbability() {
        int size = 1000;
        Faker fakerWithProbabilityConfig;
        double[] probabilities = {0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1};
        for (double probability : probabilities) {
            fakerWithProbabilityConfig =
                new Faker().withProbabilityConfig(new ProbabilityConfig().with(true, probability));
            List<Boolean> randomBooleans = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                randomBooleans.add(fakerWithProbabilityConfig.bool().bool());
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

}
