package net.datafaker.providers.base;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OptionsTest extends BaseFakerTest<BaseFaker> {

    private final String[] options = {"A", "B", "C"};
    private final Options opt = faker.options();

    @Test
    void testOptionWithArray() {
        assertThat(opt.option(options)).isIn((Object[]) options);
    }

    @Test
    void testOptionWithVarargsString() {
        assertThat(opt.option("A", "B", "C")).isIn((Object[]) options);
    }

    @Test
    void testOptionWithVarargs() {
        Integer[] integerOptions = {1, 3, 4, 5};
        assertThat(opt.option(1, 3, 4, 5)).isIn((Object[]) integerOptions);
        Long[] longOptions = {1L, 3L, 4L, 5L};
        assertThat(opt.option(longOptions)).isIn((Object[]) longOptions);
        Short[] shortOptions = {1, 3, 4};
        assertThat(opt.option(shortOptions)).isIn((Object[]) shortOptions);
        Byte[] byteOptions = {(byte) 11, (byte) 13, (byte) 14};
        assertThat(opt.option(byteOptions)).isIn((Object[]) byteOptions);
        Double[] doubleOptions = {1.1d, 13d, 14.2d};
        assertThat(opt.option(doubleOptions)).isIn((Object[]) doubleOptions);
        Float[] floatOptions = {1.2f, 13f, 14.2f};
        assertThat(opt.option(floatOptions)).isIn((Object[]) floatOptions);
        BigInteger[] bigIntegerOptions = {BigInteger.ONE, BigInteger.TEN, BigInteger.ZERO};
        assertThat(opt.option(bigIntegerOptions)).isIn((Object[]) bigIntegerOptions);
        BigDecimal[] bigDecimalOptions = {BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO};
        assertThat(opt.option(bigDecimalOptions)).isIn((Object[]) bigDecimalOptions);
        Boolean[] booleanOptions = {true, false};
        assertThat(opt.option(booleanOptions)).isIn((Object[]) booleanOptions);
    }

    @Test
    void testSubset() {
        Integer[] integerOptions = {1, 3, 4, 5};
        assertThat(opt.subset(1, integerOptions))
            .doesNotContainAnyElementsOf(List.of(2, 6))
            .containsAnyElementsOf(List.of(integerOptions));
        Long[] longOptions = {1L, 3L, 4L, 5L};
        assertThat(opt.subset(1, longOptions))
            .doesNotContainAnyElementsOf(List.of(2L, 6L))
            .containsAnyElementsOf(List.of(longOptions));

        assertThat(opt.subset(longOptions.length, longOptions))
            .doesNotContainAnyElementsOf(List.of(2L, 6L))
            .containsAnyElementsOf(List.of(longOptions)).hasSameSizeAs(longOptions);

        assertThat(opt.subset(longOptions.length + 1, longOptions))
            .doesNotContainAnyElementsOf(List.of(2L, 6L))
            .containsAnyElementsOf(List.of(longOptions)).hasSameSizeAs(longOptions);

        String[] strOptions = {"1", "2", "3"};
        assertThat(opt.subset(strOptions.length + 1, strOptions))
            .doesNotContainAnyElementsOf(List.of("q", "w"))
            .containsAnyElementsOf(List.of(strOptions)).hasSameSizeAs(strOptions);

        assertThat(opt.subset(1, strOptions))
            .doesNotContainAnyElementsOf(List.of("q", "w"))
            .containsAnyElementsOf(List.of(strOptions))
            .hasSize(1);

    }

    @Test
    void testSubsetWithDuplicate() {
        Object[] array = {1, 1, 2, 2};
        assertThat(opt.subset(5, array)).hasSize(2);
        String[] strArray = {"a", "s", "s", "a"};
        assertThat(opt.subset(Integer.MAX_VALUE, strArray)).hasSize(2);
    }

    @Test
    void testEmptySubset() {
        Object[] array = {1, 2, 3};
        assertThat(opt.subset(0, array)).isEmpty();
        assertThatThrownBy(() -> opt.subset(-1, array))
            .isInstanceOf(IllegalArgumentException.class);
        String[] strArray = {"1", "2", "3"};
        assertThat(opt.subset(0, strArray)).isEmpty();
        assertThatThrownBy(() -> opt.subset(-1, strArray)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testOptionWithEnum() {
        assertThat(opt.option(Day.class)).isIn((Object[]) Day.values());
    }

    @Test
    void testNextArrayElement() {
        Integer[] array = {1, 2, 3, 5, 8, 13, 21};

        for (int i = 1; i < 10; i++) {
            assertThat(opt.nextElement(array)).isIn((Object[]) array);
        }
    }

    @Test
    void testNextListElement() {
        List<Integer> list = List.of(1, 2, 3, 5, 8, 13, 21);
        for (int i = 1; i < 10; i++) {
            assertThat(opt.nextElement(list)).isIn(list);
        }
    }

    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
