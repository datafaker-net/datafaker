package net.datafaker.junit;

import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @FakerSource} is an {@link ArgumentsSource} for
 * {@link org.junit.jupiter.params.ParameterizedTest} that generates test arguments
 * by invoking a Datafaker provider method via reflection.
 *
 * <h3>Descriptor syntax</h3>
 * <p>
 * Full form with parameters:
 * <pre>{@code
 * code = "fieldName#methodName(ParamType1, ParamType2, ...)"
 * }</pre>
 * <p>
 * Short form for no-argument methods (parentheses optional):
 * <pre>{@code
 * code = "fieldName#methodName"
 * }</pre>
 * <ul>
 *   <li>{@code fieldName}  – field in the test class holding a Datafaker provider instance,
 *       or a dot-separated path starting from a field (e.g. {@code "NL_FAKER.address"})</li>
 *   <li>{@code methodName} – method to invoke on the resolved provider</li>
 *   <li>{@code ParamType}  – simple or fully-qualified name of an {@code Enum},
 *       primitive ({@code int}, {@code long}, {@code boolean}), or {@code String}</li>
 * </ul>
 *
 * <h3>Parameter values</h3>
 * <p>
 * Use {@link #params()} for a single value per parameter position,
 * or {@link #multiParams()} for multiple alternative values per position.
 * Both attributes cannot be combined.
 *
 * <table border="1">
 *   <tr><th>Attribute</th><th>Behaviour</th></tr>
 *   <tr>
 *     <td><em>neither set (default)</em></td>
 *     <td>All constants of every declared enum type are used;
 *         their cartesian product forms the argument combinations.</td>
 *   </tr>
 *   <tr>
 *     <td>{@code params = "VISA"}</td>
 *     <td>Shorthand for a single value per parameter position.</td>
 *   </tr>
 *   <tr>
 *     <td>{@code multiParams = @Param({"DE", "AT"})}</td>
 *     <td>Multiple alternative values for one parameter position,
 *         producing one combination per value.</td>
 *   </tr>
 * </table>
 *
 * <h3>Locale</h3>
 * <p>
 * When {@link #locale()} is set, a fresh {@link net.datafaker.Faker} for that locale
 * is created and used as the source, overriding any field resolved from the test class.
 * The value must be a valid IETF BCP 47 language tag (e.g. {@code "es-AR"}, {@code "nl-NL"}).
 *
 * <h3>Examples</h3>
 * <pre>{@code
 * // All CreditCardType constants × 100 repetitions
 * @ParameterizedTest(name = "[{index}] {0}")
 * @FakerSource(code = "finance#creditCard(CreditCardType)", repeat = 100)
 * void allCardTypes(String card) { ... }
 *
 * // Only VISA, 100 repetitions
 * @ParameterizedTest(name = "[{index}] {0}")
 * @FakerSource(code = "finance#creditCard(CreditCardType)", params = "VISA", repeat = 100)
 * void visaOnly(String card) { ... }
 *
 * // VISA and MASTERCARD via multiParams
 * @ParameterizedTest(name = "[{index}] {0}")
 * @FakerSource(code = "finance#creditCard(CreditCardType)", multiParams = @FakerSource.Param({"VISA", "MASTERCARD"}), repeat = 50)
 * void twoTypes(String card) { ... }
 *
 * // Argentine zip codes – locale + distinct replaces @MethodSource
 * @ParameterizedTest(name = "[{index}] {0}")
 * @FakerSource(code = "address#zipCode", locale = "es-AR", repeat = 4025, distinct = true)
 * void testArgentineZipCodes(String zipCode) { ... }
 *
 * // Provider object as argument (no method call)
 * @ParameterizedTest(name = "[{index}] {0}")
 * @FakerSource(code = "address", locale = "nl-BE", repeat = 10)
 * void belgianAddress(Address address) { ... }
 * }</pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(FakerSourceProvider.class)
public @interface FakerSource {

    /**
     * Reflection descriptor: {@code "fieldName#methodName(ParamType1, ParamType2, ...)"}.
     * <p>
     * Type names are resolved in this order:
     * <ol>
     *   <li>Inner classes of the resolved provider (e.g. {@code Finance.CreditCardType})</li>
     *   <li>Fully-qualified class name</li>
     *   <li>Same package as the test class</li>
     *   <li>Inner or nested classes of the test class</li>
     * </ol>
     */
    String code();

    /**
     * Shorthand for providing exactly one value per parameter position.
     * <p>
     * Cannot be combined with {@link #multiParams()}.
     * <p>
     * Each entry is resolved as:
     * <ul>
     *   <li>an enum constant name (e.g. {@code "VISA"} or {@code "CreditCardType.VISA"})</li>
     *   <li>an integer literal (e.g. {@code "3"})</li>
     *   <li>a {@code String} value (used as-is)</li>
     * </ul>
     */
    String[] params() default {};

    /**
     * Advanced form for providing multiple alternative values per parameter position.<br>
     * Each {@link Param} corresponds to one parameter position and lists the values
     * to use for that position; their cartesian product forms the argument combinations.
     * <p>
     * Cannot be combined with {@link #params()}.
     */
    Param[] multiParams() default {};

    /**
     * IETF BCP 47 language tag for the Faker locale (e.g. {@code "es-AR"}, {@code "nl-NL"}).
     * <p>
     * When set, a fresh {@link net.datafaker.Faker} with this locale is created and used
     * as the source, overriding any field resolved from the test class.
     * When empty (the default), the field resolved from the test class is used.
     */
    String locale() default "";

    /**
     * When {@code true}, duplicate generated values are removed from the argument stream.<br>
     * Useful with a high {@link #repeat()} count to produce a set of unique values.<br>
     * Defaults to {@code false}.
     */
    boolean distinct() default false;

    /**
     * Number of times each argument combination is generated. Defaults to {@code 1}.
     */
    int repeat() default 1;

    /**
     * A list of alternative string values for a single parameter position,
     * used with {@link FakerSource#multiParams()}.
     *
     * <p>
     * Example:
     * <pre>{@code
     * @FakerSource(
     *     code        = "finance#creditCard(CreditCardType)",
     *     multiParams = @FakerSource.Param({"VISA", "MASTERCARD"}),
     *     repeat      = 50
     * )
     * }</pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @interface Param {
        /** One or more string values for this parameter position. */
        String[] value();
    }

}

