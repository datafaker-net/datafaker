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

    @Test
    void testOneOfAnArray() {
        char[] charOptions = {'a', 'b', 'c', 'd'};
        assertThat(charOptions).contains(selection.oneOf(charOptions));
        int[] intOptions = {1, 3, 4, 5};
        assertThat(intOptions).contains(selection.oneOf(intOptions));
        long[] longOptions = {1L, 3L, 4L, 5L};
        assertThat(longOptions).contains(selection.oneOf(longOptions));
        float[] floatOptions = {1.2f, 13f, 14.2f};
        assertThat(floatOptions).contains(selection.oneOf(floatOptions));
        double[] doubleOptions = {1.1d, 13d, 14.2d};
        assertThat(doubleOptions).contains(selection.oneOf(doubleOptions));
        short[] shortOptions = {1, 3, 4};
        assertThat(shortOptions).contains(selection.oneOf(shortOptions));
        boolean[] booleanOptions = {true, false};
        assertThat(booleanOptions).contains(selection.oneOf(booleanOptions));
        byte[] byteOptions = {(byte) 11, (byte) 13, (byte) 14};
        assertThat(byteOptions).contains(selection.oneOf(byteOptions));
        String[] stringOptions = {"A", "B", "C"};
        assertThat(selection.oneOf(stringOptions)).isIn(List.of(stringOptions));
    }

    @Test
    void testOneOfVarargs() {
        assertThat(selection.oneOf('a', 'b', 'c', 'd')).isIn('a', 'b', 'c', 'd');
        assertThat(selection.oneOf(1, 3, 4, 5)).isIn(1, 3, 4, 5);
        assertThat(selection.oneOf(1L, 3L, 4L, 5L)).isIn(1L, 3L, 4L, 5L);
        assertThat(selection.oneOf(1.2f, 13f, 14.2f)).isIn(1.2f, 13f, 14.2f);
        assertThat(selection.oneOf(1.1d, 13d, 14.2d)).isIn(1.1d, 13d, 14.2d);
        assertThat(selection.oneOf((short) 1, (short) 3, (short) 4)).isIn((short) 1, (short) 3, (short) 4);
        assertThat(selection.oneOf(true, false)).isIn(true, false);
        assertThat(selection.oneOf((byte) 11, (byte) 13, (byte) 14)).isIn((byte) 11, (byte) 13, (byte) 14);
        assertThat(selection.oneOf("A", "B", "C")).isIn("A", "B", "C");
        assertThat(selection.oneOf(BigInteger.ONE, BigInteger.TEN, BigInteger.ZERO)).isIn(BigInteger.ONE, BigInteger.TEN, BigInteger.ZERO);
        assertThat(selection.oneOf(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO)).isIn(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO);
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
