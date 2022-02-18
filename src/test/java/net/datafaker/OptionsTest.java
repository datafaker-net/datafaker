package net.datafaker;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;

public class OptionsTest extends AbstractFakerTest {

    private String[] options;

    @Before
    public void setupOptions() {
        options = new String[]{"A", "B", "C"};
    }

    @Test
    public void testOptionWithArray() {
        assertThat(faker.options().option(options), is(oneOf(options)));
    }

    @Test
    public void testOptionWithVarargsString() {
        assertThat(faker.options().option("A", "B", "C"), is(oneOf(options)));
    }

    @Test
    public void testOptionWithVarargs() {
        Integer[] integerOptions = new Integer[]{1, 3, 4, 5};
        assertThat(faker.options().option(1, 3, 4, 5), is(oneOf(integerOptions)));
        Long[] longOptions = new Long[]{1L, 3L, 4L, 5L};
        assertThat(faker.options().option(longOptions), is(oneOf(longOptions)));
        Short[] shortOptions = new Short[]{1, 3, 4};
        assertThat(faker.options().option(shortOptions), is(oneOf(shortOptions)));
        Byte[] byteOptions = new Byte[]{(byte)11, (byte)13, (byte)14};
        assertThat(faker.options().option(byteOptions), is(oneOf(byteOptions)));
        Double[] doubleOptions = new Double[]{1.1d, 13d, 14.2d};
        assertThat(faker.options().option(doubleOptions), is(oneOf(doubleOptions)));
        Float[] floatOptions = new Float[]{1.2f, 13f, 14.2f};
        assertThat(faker.options().option(floatOptions), is(oneOf(floatOptions)));
        BigInteger[] bigIntegerOptions = new BigInteger[]{BigInteger.ONE, BigInteger.TEN, BigInteger.ZERO};
        assertThat(faker.options().option(bigIntegerOptions), is(oneOf(bigIntegerOptions)));
        BigDecimal[] bigDecimalOptions = new BigDecimal[]{BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO};
        assertThat(faker.options().option(bigDecimalOptions), is(oneOf(bigDecimalOptions)));
        Boolean[] booleanOptions = new Boolean[]{true, false};
        assertThat(faker.options().option(booleanOptions), is(oneOf(booleanOptions)));
    }

    @Test
    public void testOptionWithEnum() {
        assertThat(faker.options().option(Day.class), is(oneOf(Day.values())));
    }

    @Test
    public void testNextArrayElement() {
        Integer[] array = new Integer[]{1, 2, 3, 5, 8, 13, 21};

        for (int i = 1; i < 10; i++) {
            assertThat(faker.options().nextElement(array), is(in(array)));
        }
    }

    @Test
    public void testNextListElement() {
        List<Integer> list = Arrays.asList(1, 2, 3, 5, 8, 13, 21);
        for (int i = 1; i < 10; i++) {
            assertThat(faker.options().nextElement(list), is(in(list)));
        }
    }

    public enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
