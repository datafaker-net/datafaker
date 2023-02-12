package net.datafaker.providers.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OptionsTest extends BaseFakerTest<BaseFaker> {

    private String[] options;

    @BeforeEach
    void setupOptions() {
        options = new String[]{"A", "B", "C"};
    }

    @Test
    void testOptionWithArray() {
        assertThat(faker.options().option(options)).isIn((Object[]) options);
    }

    @Test
    void testOptionWithVarargsString() {
        assertThat(faker.options().option("A", "B", "C")).isIn((Object[]) options);
    }

    @Test
    void testOptionWithVarargs() {
        Integer[] integerOptions = new Integer[]{1, 3, 4, 5};
        assertThat(faker.options().option(1, 3, 4, 5)).isIn((Object[]) integerOptions);
        Long[] longOptions = new Long[]{1L, 3L, 4L, 5L};
        assertThat(faker.options().option(longOptions)).isIn((Object[]) longOptions);
        Short[] shortOptions = new Short[]{1, 3, 4};
        assertThat(faker.options().option(shortOptions)).isIn((Object[]) shortOptions);
        Byte[] byteOptions = new Byte[]{(byte) 11, (byte) 13, (byte) 14};
        assertThat(faker.options().option(byteOptions)).isIn((Object[]) byteOptions);
        Double[] doubleOptions = new Double[]{1.1d, 13d, 14.2d};
        assertThat(faker.options().option(doubleOptions)).isIn((Object[]) doubleOptions);
        Float[] floatOptions = new Float[]{1.2f, 13f, 14.2f};
        assertThat(faker.options().option(floatOptions)).isIn((Object[]) floatOptions);
        BigInteger[] bigIntegerOptions = new BigInteger[]{BigInteger.ONE, BigInteger.TEN, BigInteger.ZERO};
        assertThat(faker.options().option(bigIntegerOptions)).isIn((Object[]) bigIntegerOptions);
        BigDecimal[] bigDecimalOptions = new BigDecimal[]{BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO};
        assertThat(faker.options().option(bigDecimalOptions)).isIn((Object[]) bigDecimalOptions);
        Boolean[] booleanOptions = new Boolean[]{true, false};
        assertThat(faker.options().option(booleanOptions)).isIn((Object[]) booleanOptions);
    }

    @Test
    void testSubset() {
        Integer[] integerOptions = new Integer[]{1, 3, 4, 5};
        assertThat(faker.options().subset(1, integerOptions))
            .doesNotContainAnyElementsOf(List.of(2, 6))
            .containsAnyElementsOf(List.of(integerOptions));
        Long[] longOptions = new Long[]{1L, 3L, 4L, 5L};
        assertThat(faker.options().subset(1, longOptions))
            .doesNotContainAnyElementsOf(List.of(2L, 6L))
            .containsAnyElementsOf(List.of(longOptions));

        assertThat(faker.options().subset(longOptions.length, longOptions))
            .doesNotContainAnyElementsOf(List.of(2L, 6L))
            .containsAnyElementsOf(List.of(longOptions)).hasSameSizeAs(longOptions);

        assertThat(faker.options().subset(longOptions.length + 1, longOptions))
            .doesNotContainAnyElementsOf(List.of(2L, 6L))
            .containsAnyElementsOf(List.of(longOptions)).hasSameSizeAs(longOptions);

        String[] strOptions = new String[]{"1", "2", "3"};
        assertThat(faker.options().subset(strOptions.length + 1, strOptions))
            .doesNotContainAnyElementsOf(List.of("q", "w"))
            .containsAnyElementsOf(List.of(strOptions)).hasSameSizeAs(strOptions);

        assertThat(faker.options().subset(1, strOptions))
            .doesNotContainAnyElementsOf(List.of("q", "w"))
            .containsAnyElementsOf(List.of(strOptions))
            .hasSize(1);

    }

    @Test
    void testSubsetWithDuplicate() {
        Object[] array = new Object[]{1, 1, 2, 2};
        assertThat(faker.options().subset(5, array)).hasSize(2);
        String[] strArray = new String[]{"a", "s", "s", "a"};
        assertThat(faker.options().subset(Integer.MAX_VALUE, strArray)).hasSize(2);
    }

    @Test
    void testEmptySubset() {
        Object[] array = new Object[]{1, 2, 3};
        assertThat(faker.options().subset(0, array)).isEmpty();
        assertThatThrownBy(() -> faker.options().subset(-1, array))
            .isInstanceOf(IllegalArgumentException.class);
        String[] strArray = new String[]{"1", "2", "3"};
        assertThat(faker.options().subset(0, strArray)).isEmpty();
        assertThatThrownBy(() -> faker.options().subset(-1, strArray)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testOptionWithEnum() {
        assertThat(faker.options().option(Day.class)).isIn((Object[]) Day.values());
    }

    @Test
    void testNextArrayElement() {
        Integer[] array = new Integer[]{1, 2, 3, 5, 8, 13, 21};

        for (int i = 1; i < 10; i++) {
            assertThat(faker.options().nextElement(array)).isIn((Object[]) array);
        }
    }

    @Test
    void testNextListElement() {
        List<Integer> list = List.of(1, 2, 3, 5, 8, 13, 21);
        for (int i = 1; i < 10; i++) {
            assertThat(faker.options().nextElement(list)).isIn(list);
        }
    }

    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
