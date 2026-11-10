package net.datafaker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WeightedYamlValuesTest {
    @Test
    void ordinaryListsAreNotWeighted() {
        for (List<?> list : List.of(List.of(), List.of("a", "b"), List.of(Map.of("plain", "value")))) {
            assertThat(WeightedYamlValues.from(list, UnaryOperator.identity())).isNull();
        }
    }

    @Test
    void followsExistingSelectorAtCumulativeBoundaries() {
        var values = WeightedYamlValues.from(List.of(Map.of("60%", "first"), Map.of("40%", "second")), UnaryOperator.identity());
        // Preserve the existing selector's exact-boundary convention, including 60 itself.
        for (double draw : new double[]{0, Math.nextDown(0.6), 0.6}) {
            assertThat(values.select(fixed(draw, 0))).isEqualTo("first");
        }
        for (double draw : new double[]{Math.nextUp(0.6), Math.nextDown(1.0)}) {
            assertThat(values.select(fixed(draw, 0))).isEqualTo("second");
        }
    }

    @Test
    void selectsBranchThenUniformListEntry() {
        var values = WeightedYamlValues.from(List.of(Map.of("90%", ".it"), Map.of("10%", List.of(".ee", ".lv", ".lt"))), UnaryOperator.identity());
        assertThat(values.select(fixed(0, 2))).isEqualTo(".it");
        for (int i = 0; i < 3; i++) {
            assertThat(values.select(fixed(0.99, i))).isEqualTo(List.of(".ee", ".lv", ".lt").get(i));
        }
    }

    @Test
    void supportsAnyFakerRandomGeneratorWithoutIndependentRandomness() {
        RandomGenerator generator = new RandomGenerator() {
            @Override
            public long nextLong() {
                throw new AssertionError("Unexpected independent random draw");
            }

            @Override
            public double nextDouble() {
                return 0.99;
            }
        };
        var values = WeightedYamlValues.from(List.of(Map.of("60%", "first"), Map.of("40%", "second")), UnaryOperator.identity());
        assertThat(values.select(new RandomService(generator))).isEqualTo("second");
    }

    @Test
    void samplingDoesNotCacheContextOrOutput() {
        var values = WeightedYamlValues.from(List.of(Map.of("60%", "first"), Map.of("40%", "second")), UnaryOperator.identity());
        assertThat(values.select(fixed(0, 0))).isEqualTo("first");
        assertThat(values.select(fixed(0.99, 0))).isEqualTo("second");
        assertThat(values.select(fixed(0, 0))).isEqualTo("first");
        RandomService a = new RandomService(new Random(123));
        RandomService b = new RandomService(new Random(123));
        for (int i = 0; i < 100; i++) assertThat(values.select(a)).isEqualTo(values.select(b));
    }

    @Test
    void qualifiesBothBranchShapesWithoutChangingSource() {
        List<?> source = List.of(Map.of("60%", "#{suffix}"), Map.of("40%", List.of("#{other}")));
        var values = WeightedYamlValues.from(source, s -> s.replace("#{", "#{Custom."));
        assertThat(values.select(fixed(0, 0))).isEqualTo("#{Custom.suffix}");
        assertThat(values.select(fixed(0.99, 0))).isEqualTo("#{Custom.other}");
        assertThat(source.get(0)).isEqualTo(Map.of("60%", "#{suffix}"));
    }

    @Test
    void sumsDecimalPercentagesExactly() {
        var values = WeightedYamlValues.from(List.of(Map.of("33.3%", "a"), Map.of("33.3%", "b"), Map.of("33.4%", "c")), UnaryOperator.identity());
        assertThat(values.select(fixed(0.99, 0))).isEqualTo("c");
    }

    @Test
    void acceptsDuplicateStringAndListBranches() {
        var strings = WeightedYamlValues.from(List.of(Map.of("60%", "same"), Map.of("40%", "same")), UnaryOperator.identity());
        var lists = WeightedYamlValues.from(List.of(Map.of("60%", List.of("a", "b")), Map.of("40%", List.of("a", "b"))), UnaryOperator.identity());
        for (double draw : new double[]{0, 0.99}) {
            assertThat(strings.select(fixed(draw, 0))).isEqualTo("same");
            assertThat(lists.select(fixed(draw, 1))).isEqualTo("b");
        }
    }

    @Test
    void preparesChoicesOnceWithoutRetainingMutableInput() {
        List<String> branch = new ArrayList<>(List.of("a", "b"));
        Map<String, Object> entry = new HashMap<>(Map.of("100%", branch));
        List<Map<String, Object>> source = new ArrayList<>(List.of(entry));
        int[] qualified = {0};
        var values = WeightedYamlValues.from(source, string -> {
            qualified[0]++;
            return string;
        });
        branch.clear();
        entry.clear();
        source.clear();
        assertThat(values.select(fixed(0, 0))).isEqualTo("a");
        assertThat(values.select(fixed(0.99, 1))).isEqualTo("b");
        assertThat(qualified[0]).isEqualTo(2);
    }

    @ParameterizedTest
    @MethodSource("invalidChoices")
    void rejectsMalformedChoices(List<?> choices) {
        assertThatThrownBy(() -> WeightedYamlValues.from(choices, UnaryOperator.identity()))
            .isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith("Invalid weighted YAML choices:");
    }

    static Stream<List<?>> invalidChoices() {
        return Stream.of(
            List.of(Map.of("60%", "a"), "b"),
            List.of(Map.of("60%", "a"), Map.of("plain", "b")),
            List.of(Map.of("60%", "a"), Map.of()),
            List.of(Map.of("60%", "a", "40%", "b")),
            List.of(Map.of("0%", "a"), Map.of("100%", "b")),
            List.of(Map.of("-1%", "a"), Map.of("101%", "b")),
            List.of(Map.of("NaN%", "a")),
            List.of(Map.of("Infinity%", "a")),
            List.of(Map.of("60 %", "a")),
            List.of(Map.of("60%", "a"), Map.of("30%", "b")),
            List.of(Map.of("60%", "a"), Map.of("50%", "b")),
            List.of(Map.of("100%", List.of())),
            List.of(Map.of("100%", List.of("a", 1))),
            List.of(Map.of("100%", Map.of("nested", "a"))),
            List.of(Map.of("100%", List.of(Map.of("100%", "a")))),
            List.of(Map.of("9".repeat(400) + "%", "overflow")),
            List.of(Map.of("0." + "0".repeat(400) + "1%", "underflow"))
        );
    }

    private static RandomService fixed(double draw, int index) {
        return new RandomService(new Random(0) {
            @Override
            public double nextDouble() {
                return draw;
            }

            @Override
            public int nextInt(int bound) {
                if (index < 0 || index >= bound) throw new AssertionError("Unexpected list size " + bound);
                return index;
            }
        });
    }
}
