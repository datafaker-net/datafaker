package net.datafaker.providers.base;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SelectionTest {

    private final Selection selection = new Faker().selection();
    private final String[] options = {"A", "B", "C"};

    @Test
    void testOneOfAnArray() {
        assertThat(selection.oneOf(options)).isIn((Object[]) options);
    }

    @Test
    void testOneOfStringVarargs() {
        assertThat(selection.oneOf("A", "B", "C")).isIn((Object[]) options);
    }

    @Test
    void testOneOfVarargs() {
        Integer[] integerOptions = {1, 3, 4, 5};
        assertThat(selection.oneOf(1, 3, 4, 5)).isIn((Object[]) integerOptions);
        Long[] longOptions = {1L, 3L, 4L, 5L};
        assertThat(selection.oneOf(longOptions)).isIn((Object[]) longOptions);
        Short[] shortOptions = {1, 3, 4};
        assertThat(selection.oneOf(shortOptions)).isIn((Object[]) shortOptions);
        Byte[] byteOptions = {(byte) 11, (byte) 13, (byte) 14};
        assertThat(selection.oneOf(byteOptions)).isIn((Object[]) byteOptions);
        Double[] doubleOptions = {1.1d, 13d, 14.2d};
        assertThat(selection.oneOf(doubleOptions)).isIn((Object[]) doubleOptions);
        Float[] floatOptions = {1.2f, 13f, 14.2f};
        assertThat(selection.oneOf(floatOptions)).isIn((Object[]) floatOptions);
        BigInteger[] bigIntegerOptions = {BigInteger.ONE, BigInteger.TEN, BigInteger.ZERO};
        assertThat(selection.oneOf(bigIntegerOptions)).isIn((Object[]) bigIntegerOptions);
        BigDecimal[] bigDecimalOptions = {BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO};
        assertThat(selection.oneOf(bigDecimalOptions)).isIn((Object[]) bigDecimalOptions);
        Boolean[] booleanOptions = {true, false};
        assertThat(selection.oneOf(booleanOptions)).isIn((Object[]) booleanOptions);
    }

    @Test
    void testSetOf() {
        Integer[] integerOptions = {1, 3, 4, 5};
        assertThat(selection.setOf(1, integerOptions))
            .doesNotContainAnyElementsOf(List.of(2, 6))
            .containsAnyElementsOf(List.of(integerOptions));

        Long[] longOptions = {1L, 3L, 4L, 5L};
        assertThat(selection.setOf(1, longOptions))
            .doesNotContainAnyElementsOf(List.of(2L, 6L))
            .containsAnyElementsOf(List.of(longOptions));

        assertThat(selection.setOf(longOptions.length, longOptions))
            .doesNotContainAnyElementsOf(List.of(2L, 6L))
            .containsAnyElementsOf(List.of(longOptions)).hasSameSizeAs(longOptions);

        assertThat(selection.setOf(longOptions.length + 1, longOptions))
            .doesNotContainAnyElementsOf(List.of(2L, 6L))
            .containsAnyElementsOf(List.of(longOptions)).hasSameSizeAs(longOptions);

        String[] strOptions = {"1", "2", "3"};
        assertThat(selection.setOf(strOptions.length + 1, strOptions))
            .doesNotContainAnyElementsOf(List.of("q", "w"))
            .containsAnyElementsOf(List.of(strOptions)).hasSameSizeAs(strOptions);

        assertThat(selection.setOf(1, strOptions))
            .doesNotContainAnyElementsOf(List.of("q", "w"))
            .containsAnyElementsOf(List.of(strOptions))
            .hasSize(1);

    }

    @Test
    void testSetOfDuplicates() {
        Object[] array = {1, 1, 2, 2};
        assertThat(selection.setOf(5, array)).hasSize(2);
        String[] strArray = {"a", "s", "s", "a"};
        assertThat(selection.setOf(Integer.MAX_VALUE, strArray)).hasSize(2);
    }

    @Test
    void testEmptySetOf() {
        Object[] array = {1, 2, 3};
        assertThat(selection.setOf(0, array)).isEmpty();
        assertThatThrownBy(() -> selection.setOf(-1, array))
            .isInstanceOf(IllegalArgumentException.class);
        String[] strArray = {"1", "2", "3"};
        assertThat(selection.setOf(0, strArray)).isEmpty();
        assertThatThrownBy(() -> selection.setOf(-1, strArray)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testOneOfAnEnum() {
        assertThat(selection.oneOf(Day.class)).isIn((Object[]) Day.values());
    }

    @Test
    void testOneOfAList() {
        List<Integer> list = List.of(1, 2, 3, 5, 8, 13, 21);
        for (int i = 1; i < 10; i++) {
            assertThat(selection.oneOf(list)).isIn(list);
        }
    }

    @Test
    void testOneOfACollection() {
        Collection<Integer> collection = Set.of(1, 2, 3, 5, 8, 13, 21);
        for (int i = 1; i < 10; i++) {
            assertThat(selection.oneOf(collection)).isIn(collection);
        }
    }

    @Test
    void testOneOfAnEmptyCollection() {
        Collection<Integer> collection = Set.of();
        assertThatThrownBy(() -> selection.oneOf(collection))
            .isInstanceOf(IllegalArgumentException.class);
    }

    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
